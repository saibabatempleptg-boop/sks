package com.example.model

data class SevaService(
    val id: String,
    val title: String,
    val titleTe: String,
    val description: String,
    val category: String, // "Annadanam", "Abhishek", "Homam", "Maintenance", "Hundi"
    val icon: String,
    val suggestedAmounts: List<Int>,
    val minimumAmount: Int = 51
)

enum class PaymentMethod(
    val title: String,
    val icon: String,
    val description: String
) {
    UPI_GPAY("Google Pay / PhonePe UPI", "📱", "Instant transfer via UPI ID / QR"),
    UPI_PAYTM("Paytm / BHIM UPI", "💳", "Direct UPI payment gateway"),
    NET_BANKING("Net Banking", "🏛️", "All major Indian banks supported"),
    CARD("Debit / Credit Card", "💳", "Visa, Mastercard, RuPay cards")
}

data class ContributionRecord(
    val receiptId: String,
    val sevaId: String,
    val sevaTitle: String,
    val amount: Int,
    val devoteeName: String,
    val gotram: String = "",
    val nakshatram: String = "",
    val phone: String,
    val email: String = "",
    val occasion: String = "General Devotion",
    val paymentMethod: PaymentMethod,
    val timestampMillis: Long,
    val transactionRef: String
)
