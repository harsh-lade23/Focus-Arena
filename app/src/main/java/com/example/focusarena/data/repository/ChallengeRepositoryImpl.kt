package com.example.focusarena.data.repository

import android.util.Log
import com.example.focusarena.core.utils.CHALLENGE_COLLECTION
import com.example.focusarena.core.utils.PARTICIPANT_COLLECTION
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.mapper.toDomain
import com.example.focusarena.data.mapper.toEntity
import com.example.focusarena.data.models.ChallengeEntity
import com.example.focusarena.data.models.ChallengeWithParticipants
import com.example.focusarena.data.models.ParticipantEntity
import com.example.focusarena.domain.models.Challenge
import com.example.focusarena.domain.models.ChallengeStatus
import com.example.focusarena.domain.models.ChallengeWithParticipant
import com.example.focusarena.domain.models.Participant
import com.example.focusarena.domain.repository.ChallengeRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
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

            Log.d("Firestore bug fix", "getChallenges: get participants snapshot successfully")

            val participantMap = mutableMapOf<String, Participant>()
            participants.mapNotNull {
                Log.d("Firestore bug fix", "getChallenges: Snapshot - $it")
                val participantEntity = it.toObject(ParticipantEntity::class.java)
                Log.d("Firestore bug fix", "getChallenges: got participantEntity - $participantEntity")
                val participant = participantEntity.toDomain()
                Log.d("Firestore bug fix", "getChallenges: got participant - $participant")
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

                Log.d("Firestore bug fix", "getChallenges: get challenge snapshot successfully")

                challengeWithParticipantList.addAll(
                    challenges.mapNotNull {
                        val challengeEntity = it.toObject(ChallengeEntity::class.java).toDomain()
                        Log.d("Firestore bug fix", "getChallenges: converted challenge object")
                        ChallengeWithParticipant(
                            challenge = challengeEntity,
                            participant = participantMap[challengeEntity.challengeId]!!
                        )

                    }
                )

            }

            Log.d("Firestore bug fix", "getChallenges: emit challengeWithPart... - $challengeWithParticipantList")

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
                            .toObject(ChallengeEntity::class.java)
                            ?: throw Exception("Failed to fetch challenge")
                    }

                    val participantsDeferred = async {
                        firebaseFirestore.collection(PARTICIPANT_COLLECTION)
                            .whereEqualTo("challengeId", challengeId)
                            .get()
                            .await()
                            .mapNotNull {
                                it.toObject(ParticipantEntity::class.java).toDomain()
                            }
                    }

                    val challenge = challengeDeferred.await()
                    val participants = participantsDeferred.await()


                    val challengeWithParticipants =
                        ChallengeWithParticipants(challenge.toDomain(), participants = participants)
                    emit(ResultState.Success(challengeWithParticipants))


                }

            } catch (e: Exception) {
                emit(ResultState.Error(e.localizedMessage ?: "Unknown Error Occurred"))
            }

        }.flowOn(Dispatchers.IO)

    override fun createChallenge(challenge: Challenge, winningPrize: String): Flow<ResultState<String>> = flow {
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
            ).toEntity()

            val participant = ParticipantEntity(
                participantId = participantId,
                userId = userId,
                challengeId = challengeId,
                owner = true,
                status = "ACTIVE",
                joinedAt = System.currentTimeMillis(),
                prize = winningPrize
            )

            firebaseFirestore.runTransaction { transaction ->
                transaction.set(challengeRef, challengeWithId)
                transaction.set(participantRef, participant)
            }.await()
            emit(ResultState.Success(challengeId))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "An Unexpected Error Occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun startChallenge(challengeId: String): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        try {
            val challengeDocRef = firebaseFirestore.collection(CHALLENGE_COLLECTION)
                .document(challengeId)


            val userId = firebaseAuth.currentUser?.uid
                ?: throw Exception("Unknown error occurred while fetching uid")


            firebaseFirestore.runTransaction { transaction ->
                val challengeEntity = transaction.get(challengeDocRef).toObject(ChallengeEntity::class.java)
                    ?: throw Exception("Unknown error occurred")
                if (challengeEntity.challengeStatus != ChallengeStatus.CREATED.toString()) throw Exception("Challenge already started")
                if (challengeEntity.ownerId != userId) throw Exception("Can't start the challenge. Only Admin Can start the Challenge")

                transaction.update(challengeDocRef, "challengeStatus", ChallengeStatus.ACTIVE)
            }.await()

            emit(ResultState.Success("Challenge Started Successfully"))
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
    }.flowOn(Dispatchers.IO)

    override fun joinChallenge(challengeId: String, participantPrize: String): Flow<ResultState<String>> = flow {
        emit(ResultState.Loading)
        try {
            val uid = firebaseAuth.currentUser?.uid ?: throw Exception("User id not found")

            val challengeDocRef = firebaseFirestore.collection(CHALLENGE_COLLECTION)
                .document(challengeId)

            firebaseFirestore.runTransaction { transaction ->

                val challengeEntity = transaction.get(challengeDocRef).toObject(ChallengeEntity::class.java)?:throw Exception("Challenge not found.")
                if(challengeEntity.currentParticipantsCount>=challengeEntity.participantLimit)throw Exception("Can't join, max participant limit reached")

                val participantDocRef = firebaseFirestore.collection(PARTICIPANT_COLLECTION).document()
                val participant = ParticipantEntity(
                    participantId = participantDocRef.id,
                    userId = uid,
                    challengeId = challengeId,
                    owner = false,
                    status = "ACTIVE",
                    joinedAt = System.currentTimeMillis(),
                    leftAt = null,
                    profilePictureUrl = null,
                    totalPoints =0,
                    totalStudiedMinutes = 0,
                    totalTargetMinutes =0,
                    totalActiveDays = 0,
                    prize = participantPrize
                )
                transaction.set(participantDocRef, participant)
                transaction.update(challengeDocRef, "currentParticipantsCount", challengeEntity.currentParticipantsCount+1)
            }.await()

            emit(ResultState.Success(challengeId))


        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unknown error Occurred"))
        }
    }.flowOn(Dispatchers.IO)

}











