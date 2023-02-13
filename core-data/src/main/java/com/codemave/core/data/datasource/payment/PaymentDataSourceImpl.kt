package com.codemave.core.data.datasource.payment

import com.codemave.core.database.dao.PaymentDao
import com.codemave.core.database.entity.PaymentEntity
import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class PaymentDataSourceImpl @Inject constructor(
    private val paymentDao: PaymentDao
) : PaymentDataSource {
    override suspend fun addPayment(payment: Payment) {
        paymentDao.insertOrUpdate(payment.toEntity())
    }

    override suspend fun loadPaymentsFor(category: Category): Flow<List<Payment>> {
        return paymentDao.findPaymentsByCategory(category.categoryId).map { list ->
            list.map {
                it.fromEntity()
            }
        }
    }

    override suspend fun loadAllPayments(): List<Payment> {
        return paymentDao.findAll().map {
            it.fromEntity()
        }
    }

    private fun Payment.toEntity() = PaymentEntity(
        paymentId = this.paymentId,
        categoryId = this.categoryId,
        title = this.title,
        amount = this.amount,
        date = this.date
    )

    private fun PaymentEntity.fromEntity() = Payment(
        paymentId = this.paymentId,
        categoryId = this.categoryId,
        amount = this.amount,
        date = this.date,
        title = this.title
    )
}