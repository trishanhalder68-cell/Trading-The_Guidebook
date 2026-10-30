package com.example.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FirestoreUserProfile(
    @DocumentId val userId: String = "",
    val displayName: String = "",
    val email: String? = null,
    val isVip: Boolean = false,
    val monthlyTarget: Double = 50000.0,
    val annualTarget: Double = 600000.0,
    val unlockedLevels: List<Int> = listOf(1),
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)

@IgnoreExtraProperties
data class FirestoreTransaction(
    @DocumentId val id: String = "",
    val userId: String = "",
    val title: String = "",
    val amount: Double = 0.0,
    val type: String = "EXPENSE",
    val category: String = "General",
    val date: String = "",
    @ServerTimestamp val createdAt: Timestamp? = null,
    @ServerTimestamp val updatedAt: Timestamp? = null
)
