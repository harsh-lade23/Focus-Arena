package com.example.focusarena.data.repository

import com.example.focusarena.core.utils.CHALLENGE_COLLECTION
import com.example.focusarena.core.utils.PARTICIPANT_COLLECTION
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.mapper.toDomain
import com.example.focusarena.data.models.ChallengeEntity
import com.example.focusarena.data.models.ChallengeStatus
import com.example.focusarena.data.models.ChallengeWithParticipants
import com.example.focusarena.data.models.ParticipantEntity
import com.example.focusarena.data.models.ParticipantStatus
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.ChallengeWithParticipant
import com.example.focusarena.domain.models.Participant
import com.example.focusarena.domain.repository.ChallengeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject


class ChallengeRepositoryImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore,
    private val firebaseAuth: FirebaseAuth
) : ChallengeRepository {

    //TODO - FIX ALL THE FUNCTIONS -> PROVIDE THE ENTITY AND DOMAIN CLASS OBJECT PROPERLY
    override fun getChallenges(): Flow<ResultState<List<ChallengeWithParticipant>>> = flow {
        emit(ResultState.Loading)
        try {
            val userId =
                firebaseAuth.currentUser?.uid ?: throw Exception("User ID generation failed")
            val participants = firebaseFirestore.collection(PARTICIPANT_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()


            val challengeIds = mutableListOf<String>()


            val participantMap = mutableMapOf<String, Participant>()
            participants.mapNotNull {
                val participant=it.toObject(ParticipantEntity::class.java).toDomain()
                challengeIds.add(participant.challengeId)
                participantMap.put(participant.challengeId, participant)
            }

            val chunks = challengeIds.chunked(10)

            val challengeWithParticipantList = mutableListOf<ChallengeWithParticipant>()

            for (chunk in chunks) {
                val challenges = firebaseFirestore.collection(CHALLENGE_COLLECTION)
                    .whereIn(FieldPath.documentId(), chunk)
                    .get()
                    .await()

                challengeWithParticipantList.addAll(
                    challenges.mapNotNull {
                        val challenge =it.toObject(ChallengeEntity::class.java).toDomain()
                        ChallengeWithParticipant(
                            challenge = challenge,
                            participant = participantMap[challenge.challengeId]!!
                        )
                    }
                )

            }


            emit(ResultState.Success(challengeWithParticipantList))


        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "An Unexpected Error Occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun getChallengeById(challengeId: String): Flow<ResultState<ChallengeWithParticipants>> =
        flow {
            emit(ResultState.Loading)
            try {
                coroutineScope {
                    val challengeDeferred = async {
                        firebaseFirestore.collection(CHALLENGE_COLLECTION)
                            .document(challengeId)
                            .get()
                            .await()
                            .toObject(Challenge::class.java)
                            ?: throw Exception("Failed to fetch challenge")
                    }

                    val participantsDeferred = async {
                        firebaseFirestore.collection(PARTICIPANT_COLLECTION)
                            .whereEqualTo("challengeId", challengeId)
                            .get()
                            .await()
                            .mapNotNull {
                                it.toObject(Participant::class.java)
                            }
                    }

                    val challenge=challengeDeferred.await()
                    val participants =participantsDeferred.await()


                    val challengeWithParticipants =
                        ChallengeWithParticipants(challenge, participants = participants)
                    emit(ResultState.Success(challengeWithParticipants))


                }

            }
            catch (e: Exception){
                emit(ResultState.Error(e.localizedMessage?:"Unknown Error Occurred"))
            }

        }.flowOn(Dispatchers.IO)

    override fun createChallenge(challenge: Challenge): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        try {
            val userId = firebaseAuth.currentUser?.uid
                ?: throw Exception("User ID generation failed")

            val challengeRef = firebaseFirestore.collection(CHALLENGE_COLLECTION).document()
            val participantRef = firebaseFirestore.collection(PARTICIPANT_COLLECTION).document()

            val challengeId = challengeRef.id
            val participantId = participantRef.id

            val challengeWithId = challenge.copy(
                challengeId = challengeId,
                ownerId = userId
            )

            val participant = Participant(
                participantId = participantId,
                userId = userId,
                challengeId = challengeId,
                isOwner = true,
                status = ParticipantStatus.ACTIVE,
                joinedAt = System.currentTimeMillis()
            )

            firebaseFirestore.runTransaction { transaction ->
                transaction.set(challengeRef, challengeId)
                transaction.set(participantRef, participant)
            }.await()
            emit(ResultState.Success(challengeId))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "An Unexpected Error Occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun startChallenge(challengeId: String): Flow<ResultState<String>> = flow{
        emit(ResultState.Loading)
        try {
            val challengeDocRef = firebaseFirestore.collection(CHALLENGE_COLLECTION)
                .document(challengeId)


            val userId = firebaseAuth.currentUser?.uid?: throw Exception("Unknown error occurred while fetching uid")


            firebaseFirestore.runTransaction { transaction ->
                val challenge = transaction.get(challengeDocRef).toObject(Challenge::class.java)
                    ?:throw Exception("Unknown error occurred")
                if(challenge.challengeStatus != ChallengeStatus.CREATED)throw Exception("Challenge already started")
                if(challenge.ownerId != userId)throw Exception("Can't start the challenge. Only Admin Can start the Challenge")

                transaction.update(challengeDocRef, "challengeStatus", ChallengeStatus.ACTIVE)
            }.await()

            emit(ResultState.Success("Challenge Started Successfully"))
        }
        catch (e: Exception){
            emit(ResultState.Error(e.localizedMessage?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)

}











