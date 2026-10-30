package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.FirestoreTransaction
import com.example.data.model.FirestoreUserProfile
import com.example.data.util.OperationType
import com.example.data.util.handleFirestoreError
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirestoreRepository(
    private val db: FirebaseFirestore
) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    fun observeUserProfile(userId: String): Flow<FirestoreUserProfile?> {
        val docRef = db.collection("users").document(userId)
        return docRef.snapshots()
            .map { snapshot ->
                if (snapshot.exists()) {
                    snapshot.toObject(FirestoreUserProfile::class.java)
                } else {
                    null
                }
            }
            .catch { error ->
                if (error is Exception) {
                    handleFirestoreError(error, OperationType.GET, docRef.path)
                }
                throw error
            }
    }

    suspend fun saveUserProfile(profile: FirestoreUserProfile): Result<Unit> {
        val docRef = db.collection("users").document(profile.userId)
        return try {
            val existing = docRef.get().await()
            val payload = mutableMapOf<String, Any>(
                "userId" to profile.userId,
                "displayName" to profile.displayName,
                "isVip" to profile.isVip,
                "monthlyTarget" to profile.monthlyTarget,
                "annualTarget" to profile.annualTarget,
                "unlockedLevels" to profile.unlockedLevels,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            profile.email?.let { payload["email"] = it }

            if (existing.exists() && existing.getTimestamp("createdAt") != null) {
                payload["createdAt"] = existing.getTimestamp("createdAt")!!
            } else {
                payload["createdAt"] = profile.createdAt ?: FieldValue.serverTimestamp()
            }

            docRef.set(payload).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.WRITE, docRef.path)
            Result.failure(e)
        }
    }

    fun observeTransactions(userId: String): Flow<List<FirestoreTransaction>> {
        val collRef = db.collection("users").document(userId).collection("transactions")
        return collRef.snapshots()
            .map { snapshot ->
                snapshot.toObjects(FirestoreTransaction::class.java)
            }
            .catch { error ->
                if (error is Exception) {
                    handleFirestoreError(error, OperationType.LIST, collRef.path)
                }
                throw error
            }
    }

    suspend fun addTransaction(userId: String, transaction: FirestoreTransaction): Result<String> {
        val txId = if (transaction.id.isNotBlank()) transaction.id else "tx_${UUID.randomUUID().toString().replace("-", "").take(12)}"
        val docRef = db.collection("users").document(userId).collection("transactions").document(txId)
        return try {
            val payload = mutableMapOf<String, Any>(
                "userId" to userId,
                "title" to transaction.title,
                "amount" to transaction.amount,
                "type" to transaction.type,
                "category" to transaction.category,
                "date" to transaction.date,
                "createdAt" to FieldValue.serverTimestamp(),
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.set(payload).await()
            Result.success(txId)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun updateTransaction(userId: String, transaction: FirestoreTransaction): Result<Unit> {
        require(transaction.id.isNotBlank()) { "Transaction ID must not be blank for update" }
        val docRef = db.collection("users").document(userId).collection("transactions").document(transaction.id)
        return try {
            val updates = mapOf(
                "title" to transaction.title,
                "amount" to transaction.amount,
                "type" to transaction.type,
                "category" to transaction.category,
                "date" to transaction.date,
                "updatedAt" to FieldValue.serverTimestamp()
            )
            docRef.update(updates).await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.UPDATE, docRef.path)
            Result.failure(e)
        }
    }

    suspend fun deleteTransaction(userId: String, transactionId: String): Result<Unit> {
        val docRef = db.collection("users").document(userId).collection("transactions").document(transactionId)
        return try {
            docRef.delete().await()
            Result.success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            Result.failure(e)
        }
    }
}
