package com.example.data.repository

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.model.FirestoreTransaction
import com.example.data.model.FirestoreUserProfile
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test
import java.util.UUID

class FirestoreRepositoryRuleTest : FirestoreEmulatorTestBase() {

  @Test
  fun saveUserProfile_authenticatedUser_success() = runBlocking {
    val uid = signInTestUser("user_${UUID.randomUUID().toString().take(8)}@test.com")
    val repository = FirestoreRepository(firestore)

    val profile = FirestoreUserProfile(
      userId = uid,
      displayName = "Alice Trader",
      isVip = true,
      monthlyTarget = 45000.0,
      annualTarget = 540000.0,
      unlockedLevels = listOf(1, 2)
    )

    val result = withTimeout(DEFAULT_TIMEOUT_MS) {
      repository.saveUserProfile(profile)
    }
    assertTrue(result.isSuccess)
  }

  @Test
  fun addTransaction_authenticatedOwner_createsDocument() = runBlocking {
    val uid = signInTestUser("user_${UUID.randomUUID().toString().take(8)}@test.com")
    val repository = FirestoreRepository(firestore)

    val tx = FirestoreTransaction(
      title = "Nifty 50 Dividend",
      amount = 12500.0,
      type = "INCOME",
      category = "Dividends",
      date = "2026-10-30"
    )

    val result = withTimeout(DEFAULT_TIMEOUT_MS) {
      repository.addTransaction(uid, tx)
    }
    assertTrue(result.isSuccess)
    val txId = result.getOrThrow()
    assertTrue(txId.isNotEmpty())
  }

  @Test
  fun observeTransactions_authenticatedOwner_emitsList() = runBlocking {
    val uid = signInTestUser("user_${UUID.randomUUID().toString().take(8)}@test.com")
    val repository = FirestoreRepository(firestore)

    val tx = FirestoreTransaction(
      title = "Mutual Fund SIP",
      amount = 5000.0,
      type = "EXPENSE",
      category = "Investment",
      date = "2026-10-30"
    )
    val txId = withTimeout(DEFAULT_TIMEOUT_MS) {
      repository.addTransaction(uid, tx).getOrThrow()
    }

    val list = withTimeout(FLOW_TIMEOUT_MS) {
      repository.observeTransactions(uid).first { it.any { item -> item.id == txId } }
    }
    assertTrue(list.any { it.id == txId })
  }

  @Test
  fun observeTransactions_unauthenticatedUser_failsWithPermissionDenied() = runBlocking {
    auth.signOut()
    val repository = FirestoreRepository(firestore)

    try {
      withTimeout(FLOW_TIMEOUT_MS) {
        repository.observeTransactions("unauth_${UUID.randomUUID().toString().take(8)}").first()
      }
      fail("Expected exception when unauthenticated")
    } catch (e: Exception) {
      val firestoreException = e.unwrapFirestoreException()
      assertNotNull("Expected nested FirebaseFirestoreException", firestoreException)
      assertEquals(FirebaseFirestoreException.Code.PERMISSION_DENIED, firestoreException?.code)
    }
  }

  private companion object {
    const val DEFAULT_TIMEOUT_MS = 5000L
    const val FLOW_TIMEOUT_MS = 4000L
  }
}
