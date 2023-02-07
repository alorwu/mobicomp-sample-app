package com.codemave.core.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.codemave.core.database.entity.CategoryEntity
import com.codemave.core.database.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(payment: PaymentEntity)

    @Query("SELECT * FROM payments WHERE paymentId LIKE :paymentId")
    fun findOne(paymentId: Long): Flow<PaymentEntity>

    @Query("""SELECT * from categories JOIN payments ON payments.category_id = categories.categoryId WHERE categoryId LIKE :categoryId""")
    fun findPaymentsByCategory(categoryId: Long): Flow<Map<CategoryEntity, List<PaymentEntity>>>


    @Delete
    suspend fun delete(payment: PaymentEntity)
}