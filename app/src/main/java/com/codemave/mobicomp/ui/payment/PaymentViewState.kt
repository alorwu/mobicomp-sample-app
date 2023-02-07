package com.codemave.mobicomp.ui.payment

import com.codemave.core.domain.entity.Payment

sealed interface PaymentViewState {
    object Loading: PaymentViewState
    data class Success(
        val data: List<Payment>
    ): PaymentViewState
}
