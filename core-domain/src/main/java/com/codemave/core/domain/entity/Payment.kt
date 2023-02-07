package com.codemave.core.domain.entity

import java.time.LocalDateTime

data class Payment(
    val paymentId: Long = 0,
    val title: String,
    val categoryId: Long,
    val amount: Double,
    val date: LocalDateTime
)
