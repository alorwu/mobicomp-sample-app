package com.codemave.mobicomp.ui.payment

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat.from
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codemave.core.domain.entity.Category
import com.codemave.core.domain.entity.Payment
import com.codemave.core.domain.repository.PaymentRepository
import com.codemave.mobicomp.Graph
import com.codemave.mobicomp.R
import com.codemave.mobicomp.ui.category.CategoryViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val paymentRepository: PaymentRepository,
): ViewModel() {

    private val _viewState = MutableStateFlow<PaymentViewState>(PaymentViewState.Loading)
    val uiState: StateFlow<PaymentViewState> = _viewState

    fun savePayment(payment: Payment) {
        viewModelScope.launch {
            paymentRepository.addPayment(payment)
            notifyUserOfPayment(payment)
        }
    }

    private fun notifyUserOfPayment(payment: Payment) {
        val notificationId = 10
        val builder = NotificationCompat.Builder(
            Graph.appContext,
            "channel_id"
        )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("New payment made")
            .setContentText("You paid ${payment.amount} on ${payment.date}")
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        with(from(Graph.appContext)) {
            if (ActivityCompat.checkSelfPermission(
                    Graph.appContext,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }

    private fun createNotificationChannel() {
        val name = "NotificationChannel"
        val descriptionText = "NotificationChannelDescription"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel_id", name, importance).apply {
            description = descriptionText
        }
        val notificationManager = Graph.appContext
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun loadPaymentsFor(category: Category?) {
        viewModelScope.launch {
            if (category != null) {
                paymentRepository.loadPaymentsFor(category).map {
                    _viewState.value = PaymentViewState.Success(it)
                }
            }
        }
    }

    init {
        createNotificationChannel()
    }
}

