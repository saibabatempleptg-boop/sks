package com.example.data

import com.example.model.ContributionRecord
import com.example.model.PaymentMethod
import com.example.model.SevaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

object SevaRepository {

    val sevaServices = listOf(
        SevaService(
            id = "annadanam",
            title = "Nitya Annadanam Seva",
            titleTe = "నిత్య అన్నదాన సేవ",
            description = "Sponsor sacred prasadam and hearty meals for devotees and visiting pilgrims. Feeding the hungry was considered the supreme duty by Baba.",
            category = "Annadanam",
            icon = "🍚",
            suggestedAmounts = listOf(501, 1116, 2500, 5001),
            minimumAmount = 101
        ),
        SevaService(
            id = "vastram_abhishek",
            title = "Thursday Guruvaram Vastram & Abhishek",
            titleTe = "గురువారం వస్త్ర సమర్పణ & విశేష అభిషేకం",
            description = "Sponsor the holy Guruvaram abhishek of Sri Sai Baba on the golden throne with panchamrit, holy vibhuti, and offer new silk vastrams.",
            category = "Abhishek",
            icon = "🪔",
            suggestedAmounts = listOf(1008, 2116, 5116, 11116),
            minimumAmount = 501
        ),
        SevaService(
            id = "homam",
            title = "Lakshmi Ganapathi Homam & Poojas",
            titleTe = "శ్రీ లక్ష్మీ గణపతి హోమం & అర్చనలు",
            description = "Sponsor consecrated homams performed by temple priests with sacred ahutis for family well-being, health, and obstacle removal.",
            category = "Homam",
            icon = "🔥",
            suggestedAmounts = listOf(1500, 3000, 5000),
            minimumAmount = 500
        ),
        SevaService(
            id = "maintenance",
            title = "Mandhiram & Meditation Hall Upkeep",
            titleTe = "మందిర నిర్వహణ & ధ్యాన మందిర నిధి",
            description = "Contribute toward the upkeep of the 400–500 devotee capacity halls, cool ventilation systems, clean bathrooms, and serene campus grounds.",
            category = "Maintenance",
            icon = "🏛️",
            suggestedAmounts = listOf(500, 1000, 2000, 5000),
            minimumAmount = 100
        ),
        SevaService(
            id = "flowers",
            title = "Daily Pushpa Alankaram (Floral Seva)",
            titleTe = "నిత్య పుష్పాలంకరణ & తులసి సేవ",
            description = "Offer fragrant marigold, rose, and tulsi garlands for the daily ornamentation of Sri Sai Baba and sanctum deities.",
            category = "Flowers",
            icon = "🌺",
            suggestedAmounts = listOf(251, 501, 1001),
            minimumAmount = 51
        ),
        SevaService(
            id = "hundi",
            title = "Sri Sai Kanuka / Temple Hundi",
            titleTe = "శ్రీ సాయి కానుక (హుండీ సమర్పణ)",
            description = "Direct devotion contribution to the temple hundi for religious and charitable purposes as per your wish.",
            category = "Hundi",
            icon = "🪙",
            suggestedAmounts = listOf(101, 251, 501, 1116),
            minimumAmount = 11
        )
    )

    private val _contributionHistory = MutableStateFlow<List<ContributionRecord>>(
        listOf(
            ContributionRecord(
                receiptId = "SKSDM-2026-8912",
                sevaId = "annadanam",
                sevaTitle = "Nitya Annadanam Seva",
                amount = 1116,
                devoteeName = "Devotee Family",
                gotram = "Kashyapa",
                nakshatram = "Rohini",
                phone = "+91 9876543210",
                email = "devotee@example.com",
                occasion = "In Gratitude & Peace",
                paymentMethod = PaymentMethod.UPI_GPAY,
                timestampMillis = System.currentTimeMillis() - 86400000L,
                transactionRef = "TXN_MOCK_89127394"
            )
        )
    )
    val contributionHistory: StateFlow<List<ContributionRecord>> = _contributionHistory.asStateFlow()

    fun recordContribution(
        seva: SevaService,
        amount: Int,
        devoteeName: String,
        gotram: String,
        nakshatram: String,
        phone: String,
        email: String,
        occasion: String,
        paymentMethod: PaymentMethod
    ): ContributionRecord {
        val randomNum = (1000..9999).random()
        val receiptId = "SKSDM-2026-$randomNum"
        val txnRef = "TXN_${UUID.randomUUID().toString().take(10).uppercase()}"

        val record = ContributionRecord(
            receiptId = receiptId,
            sevaId = seva.id,
            sevaTitle = seva.title,
            amount = amount,
            devoteeName = devoteeName,
            gotram = gotram,
            nakshatram = nakshatram,
            phone = phone,
            email = email,
            occasion = occasion.ifBlank { "General Devotion" },
            paymentMethod = paymentMethod,
            timestampMillis = System.currentTimeMillis(),
            transactionRef = txnRef
        )

        _contributionHistory.value = listOf(record) + _contributionHistory.value
        return record
    }

    fun formatDate(timestampMillis: Long): String {
        val sdf = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH)
        return sdf.format(Date(timestampMillis))
    }
}
