package com.codemave.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codemave.core.database.dao.CategoryDao
import com.codemave.core.database.dao.PaymentDao
import com.codemave.core.database.entity.CategoryEntity
import com.codemave.core.database.entity.PaymentEntity
import com.codemave.core.database.utils.LocalDateTimeConverter

@Database(
    entities = [PaymentEntity::class, CategoryEntity::class],
    version = 1
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
    abstract fun categoryDao(): CategoryDao
}