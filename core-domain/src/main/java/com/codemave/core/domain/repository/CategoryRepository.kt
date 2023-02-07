package com.codemave.core.domain.repository

import com.codemave.core.domain.entity.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun addCategory(category: Category): Long
    suspend fun loadCategories(): Flow<List<Category>>
}