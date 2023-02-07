package com.codemave.core.data.repository

import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.repository.CategoryRepository
import com.codemave.core.data.datasource.category.CategoryDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: CategoryDataSource
) : CategoryRepository {
    override suspend fun addCategory(category: Category): Long = dataSource.addCategory(category)

    override suspend fun loadCategories(): Flow<List<Category>> {
         return dataSource.loadCategories()
    }

}