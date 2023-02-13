package com.codemave.mobicomp.ui.category

import com.codemave.core.domain.entity.Category


sealed interface CategoryViewState {
    object Loading : CategoryViewState
    data class Error(val throwable: Throwable) : CategoryViewState
    data class Success(
        val selectedCategory: Category?,
        val data: List<Category>
    ) : CategoryViewState
}