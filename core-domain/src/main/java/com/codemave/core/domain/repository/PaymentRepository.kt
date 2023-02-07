package com.codemave.core.domain.repository

import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentRepository {
    suspend fun addPayment(payment: Payment)
    suspend fun loadPaymentsFor(category: Category): Flow<List<Payment>>
}