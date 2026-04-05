package com.example.focusarena.data.repository

import com.example.focusarena.core.utils.ResultState
import com.example.focusarena.core.utils.USER_COLLECTION
import com.example.focusarena.domain.models.User
import com.example.focusarena.domain.repository.AuthenticationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.math.sign

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
): AuthenticationRepository {
    override fun registerUser(
        user: User,
        password: String
    ): Flow<ResultState<String>> = flow {

        try {

        emit(ResultState.Loading)

        val authResult = firebaseAuth.createUserWithEmailAndPassword(user.email, password).await()
        val userId = authResult.user?.uid?:throw Exception("User ID generation failed")
        val userEntity = user.copy(userId = userId)

        try {
            firebaseFirestore.collection(USER_COLLECTION)
                .document(userId)
                .set(userEntity)
                .await()

            emit(ResultState.Success(userId))

        }
        catch (e: Exception){
            firebaseAuth.currentUser?.delete()?.await()
            throw e
        }




        }
        catch (e: FirebaseAuthException){
            emit(ResultState.Error(e.localizedMessage?:"Auth Error"))

        }
        catch (e: Exception){
            emit(ResultState.Error(e.localizedMessage?:"An Unexpected Error Occurred"))
        }


    }.flowOn(Dispatchers.IO)

    override fun loginUser(
        email: String,
        password: String
    ): Flow<ResultState<String>> = flow{
        try {
            emit(ResultState.Loading)
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid?:throw Exception("User ID generation failed")
            emit(ResultState.Success(uid))
        }
        catch (e: FirebaseAuthException){
            emit(ResultState.Error(e.localizedMessage?:"Auth Error"))

        }
        catch (e: Exception){
            emit(ResultState.Error(e.localizedMessage?:"An Unexpected Error Occurred"))

        }
    }.flowOn(Dispatchers.IO)
}