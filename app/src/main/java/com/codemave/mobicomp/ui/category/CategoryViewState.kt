package com.codemave.mobicomp.ui.category

import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import com.codemave.mobicomp.ui.payment.PaymentViewModel


sealed interface CategoryViewState {
    object Loading : CategoryViewState
    data class Error(val throwable: Throwable) : CategoryViewState
    data class Success(
        val selectedCategory: Category?,
        val data: List<Category>
    ) : CategoryViewState
}