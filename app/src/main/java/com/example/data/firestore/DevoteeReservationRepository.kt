package com.example.data.firestore

import android.content.Context
import com.example.R
import com.example.model.DevoteePrayer
import com.example.model.DevoteeReservation
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class DevoteeReservationRepository(private val db: FirebaseFirestore) {

    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(
            context.applicationContext.getString(R.string.firestore_database_id)
        )
    )

    private val auth = Firebase.auth

    private fun requireUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("Devotee must be signed in with Google before accessing reservations.")
    }

    fun observeReservations(): Flow<List<DevoteeReservation>> = flow {
        val uid = requireUserId()
        val path = "users/$uid/reservations"
        emitAll(
            db.collection("users").document(uid).collection("reservations")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(DevoteeReservation::class.java) }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    val exceptionToLog = firestoreError ?: (error as? Exception)
                    if (exceptionToLog != null && auth.currentUser?.uid == uid) {
                        handleFirestoreError(exceptionToLog, OperationType.LIST, path)
                    }
                    throw error
                }
        )
    }

    suspend fun createReservation(reservation: DevoteeReservation): String {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("reservations").document()
        val payload = hashMapOf<String, Any>(
            "userId" to uid,
            "sevaId" to reservation.sevaId,
            "sevaTitle" to reservation.sevaTitle,
            "devoteeName" to reservation.devoteeName,
            "devoteePhone" to reservation.devoteePhone,
            "gotram" to reservation.gotram,
            "preferredDate" to reservation.preferredDate,
            "status" to reservation.status,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )
        if (reservation.nakshatram.isNotBlank()) {
            payload["nakshatram"] = reservation.nakshatram
        }
        if (reservation.specialPrayer.isNotBlank()) {
            payload["specialPrayer"] = reservation.specialPrayer
        }

        try {
            docRef.set(payload).await()
            return docRef.id
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }

    suspend fun deleteReservation(reservationId: String) {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("reservations").document(reservationId)
        try {
            docRef.delete().await()
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.DELETE, docRef.path)
            throw e
        }
    }

    fun observePrayers(): Flow<List<DevoteePrayer>> = flow {
        val uid = requireUserId()
        val path = "users/$uid/prayers"
        emitAll(
            db.collection("users").document(uid).collection("prayers")
                .snapshots()
                .map { snapshot -> snapshot.toObjects(DevoteePrayer::class.java) }
                .catch { error ->
                    val firestoreError = generateSequence(error) { it.cause }
                        .filterIsInstance<FirebaseFirestoreException>()
                        .firstOrNull()
                    if (firestoreError == null && error is CancellationException) throw error
                    val exceptionToLog = firestoreError ?: (error as? Exception)
                    if (exceptionToLog != null && auth.currentUser?.uid == uid) {
                        handleFirestoreError(exceptionToLog, OperationType.LIST, path)
                    }
                    throw error
                }
        )
    }

    suspend fun submitPrayer(devoteeName: String, prayerText: String): String {
        val uid = requireUserId()
        val docRef = db.collection("users").document(uid).collection("prayers").document()
        val payload = hashMapOf<String, Any>(
            "userId" to uid,
            "devoteeName" to devoteeName,
            "prayerText" to prayerText,
            "createdAt" to FieldValue.serverTimestamp(),
            "updatedAt" to FieldValue.serverTimestamp()
        )
        try {
            docRef.set(payload).await()
            return docRef.id
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            handleFirestoreError(e, OperationType.CREATE, docRef.path)
            throw e
        }
    }
}
