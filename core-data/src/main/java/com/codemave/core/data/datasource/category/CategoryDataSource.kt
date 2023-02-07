package com.codemave.core.data.datasource.category

import com.codemave.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    suspend fun addCategory(category: Category): Long
    suspend fun loadCategories(): Flow<List<Category>>
}