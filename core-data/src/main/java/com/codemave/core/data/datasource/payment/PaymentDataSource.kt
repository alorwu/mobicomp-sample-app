package com.codemave.core.data.datasource.payment

import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import kotlinx.coroutines.flow.Flow

interface PaymentDataSource {
    suspend fun addPayment(payment: Payment)
    suspend fun loadPaymentsFor(category: Category): Flow<List<Payment>>
}