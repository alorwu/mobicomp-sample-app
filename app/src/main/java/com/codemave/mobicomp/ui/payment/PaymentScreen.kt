package com.codemave.mobicomp.ui.payment

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codemave.core.domain.entity.Payment
import java.time.LocalDateTime

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val title = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }
    val category = remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {}
    )
    val context = LocalContext.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = title.value,
            onValueChange = { title.value = it },
            label = { Text(text = "Payment title") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(10.dp))
        CategoryListDropdown(
            viewModel = viewModel,
            category = category
        )
        Spacer(modifier = Modifier.height(10.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                value = amount.value,
                onValueChange = { amount.value = it },
                label = { Text(text = "Amount") },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                modifier = Modifier.fillMaxWidth(fraction = 0.5f)
            )
            OutlinedButton(
                onClick = {
                    requestPermission(
                        context = context,
                        permission = Manifest.permission.ACCESS_FINE_LOCATION,
                        requestPermission = { launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION) }
                    ).apply {
                        navController.navigate("map")
                    }
                },
                modifier = Modifier.height(55.dp)
            ) {
                Text(text = "Payment location")
            }
            Spacer(modifier = Modifier.width(10.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = true,
            onClick = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    requestPermission(
                        context = context,
                        permission = Manifest.permission.POST_NOTIFICATIONS,
                        requestPermission = { launcher.launch(Manifest.permission.POST_NOTIFICATIONS) }
                    )
                }
                viewModel.savePayment(
                    Payment(
                        title = title.value,
                        categoryId = getCategoryId(viewModel, category.value),
                        amount = amount.value.toDouble(),
                        date = LocalDateTime.now()
                    )
                )
                navController.popBackStack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .size(55.dp)
        ) {
            Text("Save payment")
        }
    }
}

private fun getCategoryId(viewModel: MainViewModel, categoryName: String): Long {
    return viewModel.categories.value.first { it.name.lowercase() == categoryName.lowercase() }.categoryId
}
@Composable
private fun CategoryListDropdown(
    viewModel: MainViewModel,
    category: MutableState<String>
) {
    val categoryState = viewModel.categories.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    val icon = if (expanded) {
        Icons.Filled.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
    } else {
        Icons.Filled.ArrowDropDown
    }

    Column {
        OutlinedTextField(
            value = category.value,
            onValueChange = { category.value = it},
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Category") },
            readOnly = true,
            trailingIcon = {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.clickable { expanded = !expanded }
                )
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            categoryState.value.forEach { dropDownOption ->
                DropdownMenuItem(
                    onClick = {
                        category.value = dropDownOption.name
                        expanded = false
                    }
                ) {
                    Text(text = dropDownOption.name)
                }
            }
        }
    }
}

private fun requestPermission(
    context: Context,
    permission: String,
    requestPermission: () -> Unit
) {
    if (ContextCompat.checkSelfPermission(
            context,
            permission
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermission()
    }
}