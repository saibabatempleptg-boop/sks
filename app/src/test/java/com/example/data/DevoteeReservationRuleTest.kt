package com.example.data

import com.example.base.FirestoreEmulatorTestBase
import com.example.data.firestore.DevoteeReservationRepository
import com.example.model.DevoteeReservation
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Assert.fail
import org.junit.Test

class DevoteeReservationRuleTest : FirestoreEmulatorTestBase() {

    @Test
    fun createReservation_authenticatedDevotee_succeeds() = runBlocking {
        val uid = signInTestUser("devotee_create@mandhiram.org")
        val repo = DevoteeReservationRepository(firestore)

        val reservation = DevoteeReservation(
            userId = uid,
            sevaId = "gotra_puja",
            sevaTitle = "Gotra Name Puja",
            devoteeName = "Devotee Alice",
            devoteePhone = "9876543210",
            gotram = "Kashyapa",
            nakshatram = "Rohini",
            preferredDate = "2026-12-01",
            specialPrayer = "Family welfare and peace"
        )

        val id = withTimeout(10000L) {
            repo.createReservation(reservation)
        }
        assertNotNull(id)
        assertTrue(id.isNotBlank())

        val list = withTimeout(10000L) {
            repo.observeReservations().first()
        }
        assertEquals(1, list.size)
        assertEquals("Gotra Name Puja", list[0].sevaTitle)
        assertEquals("Kashyapa", list[0].gotram)
    }

    @Test
    fun deleteReservation_authenticatedDevotee_succeeds() = runBlocking {
        val uid = signInTestUser("devotee_delete@mandhiram.org")
        val repo = DevoteeReservationRepository(firestore)

        val reservation = DevoteeReservation(
            userId = uid,
            sevaId = "daily_midday_prasadam",
            sevaTitle = "Daily Midday Nivedan Baba Prasadam",
            devoteeName = "Devotee Alice",
            devoteePhone = "9876543210",
            gotram = "Kashyapa",
            preferredDate = "2026-12-05"
        )

        val id = withTimeout(10000L) {
            repo.createReservation(reservation)
        }
        assertTrue(id.isNotBlank())

        withTimeout(10000L) {
            repo.deleteReservation(id)
        }

        val list = withTimeout(10000L) {
            repo.observeReservations().first()
        }
        assertTrue(list.isEmpty())
    }

    @Test
    fun unauthenticatedAccess_fails() = runBlocking {
        auth.signOut()
        val repo = DevoteeReservationRepository(firestore)

        val reservation = DevoteeReservation(
            userId = "unauthenticated",
            sevaId = "santra_prasadam",
            sevaTitle = "Santra Prasadam",
            devoteeName = "Anonymous",
            devoteePhone = "9876543210",
            gotram = "None",
            preferredDate = "2026-12-10"
        )

        try {
            repo.createReservation(reservation)
            fail("Expected exception for unauthenticated user")
        } catch (e: IllegalStateException) {
            assertTrue(e.message?.contains("Devotee must be signed in") == true)
        }
    }

    @Test
    fun crossUserAccess_deniedBySecurityRules() = runBlocking {
        val aliceUid = signInTestUser("alice_cross@mandhiram.org")
        val aliceRepo = DevoteeReservationRepository(firestore)

        val resId = withTimeout(10000L) {
            aliceRepo.createReservation(
                DevoteeReservation(
                    userId = aliceUid,
                    sevaId = "thursday_puja",
                    sevaTitle = "Thursday Guruvaram Puja",
                    devoteeName = "Alice",
                    devoteePhone = "9876543210",
                    gotram = "Kashyapa",
                    preferredDate = "2026-12-10"
                )
            )
        }

        // Now switch to Bob
        signInTestUser("bob_cross@mandhiram.org")

        // Bob tries to read Alice's reservation document directly
        try {
            firestore.collection("users").document(aliceUid).collection("reservations").document(resId).get().await()
            fail("Bob should not be able to read Alice's reservation")
        } catch (e: Exception) {
            val root = e.unwrapFirestoreException() ?: e
            assertTrue(root.message?.contains("PERMISSION_DENIED") == true || e is FirebaseFirestoreException)
        }
    }
}
