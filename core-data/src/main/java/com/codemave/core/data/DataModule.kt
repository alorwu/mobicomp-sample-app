package com.codemave.core.data

import com.codemave.core.data.datasource.category.CategoryDataSource
import com.codemave.core.data.datasource.payment.PaymentDataSource
import com.codemave.core.data.datasource.payment.PaymentDataSourceImpl
import com.codemave.core.data.repository.CategoryRepositoryImpl
import com.codemave.core.data.repository.PaymentRepositoryImpl
import com.codemave.core.domain.repository.CategoryRepository
import com.codemave.core.domain.repository.PaymentRepository
import com.mobicomp.core.data.datasource.category.CategoryDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Singleton
    @Binds
    fun bindPaymentDataSource(
        paymentDataSource: PaymentDataSourceImpl
    ): PaymentDataSource

    @Singleton
    @Binds
    fun bindPaymentRepository(
        paymentRepository: PaymentRepositoryImpl
    ): PaymentRepository

    @Singleton
    @Binds
    fun bindCategoryDataSource(
        categoryDataSource: CategoryDataSourceImpl
    ): CategoryDataSource

    @Singleton
    @Binds
    fun bindCategoryRepository(
        categoryRepository: CategoryRepositoryImpl
    ): CategoryRepository


}