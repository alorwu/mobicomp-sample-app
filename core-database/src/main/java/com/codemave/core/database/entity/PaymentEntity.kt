package com.codemave.core.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "payments",
    indices = [
        Index("paymentId", unique = true),
        Index("category_id")
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["categoryId"],
            childColumns = ["category_id"],
            onUpdate = ForeignKey.CASCADE, // when a category is updated, we update all associated payments
            onDelete = ForeignKey.CASCADE // when a category is deleted, we delete all payments associated with it
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val paymentId: Long = 0,
    val title: String,
    @ColumnInfo(name = "category_id")
    val categoryId: Long,
    val amount: Double,
    val date: LocalDateTime
)
