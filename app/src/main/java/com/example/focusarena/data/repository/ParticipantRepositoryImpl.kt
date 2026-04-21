package com.example.focusarena.data.repository

import com.example.focusarena.core.utils.CHALLENGE_COLLECTION
import com.example.focusarena.core.utils.PARTICIPANT_COLLECTION
import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.data.models.ChallengeEntity
import com.example.focusarena.data.models.ParticipantEntity
import com.example.focusarena.domain.repository.ParticipantRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ParticipantRepositoryImpl@Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): ParticipantRepository {
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