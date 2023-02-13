package com.codemave.core.data.repository

import com.codemave.core.data.datasource.payment.PaymentDataSource
import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import com.codemave.core.domain.repository.PaymentRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PaymentRepositoryImpl @Inject constructor(
    private val paymentDataSource: PaymentDataSource
) : PaymentRepository {
    override suspend fun addPayment(payment: Payment) {
        paymentDataSource.addPayment(payment)
    }

    override suspend fun loadPaymentsFor(category: Category): Flow<List<Payment>> {
        return paymentDataSource.loadPaymentsFor(category)
    }

    override suspend fun loadAllPayments(): List<Payment> {
        return paymentDataSource.loadAllPayments()
    }
}