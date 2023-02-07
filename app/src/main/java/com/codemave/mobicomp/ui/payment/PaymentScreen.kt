package com.codemave.mobicomp.ui.payment

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
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.codemave.core.domain.entity.Payment
import java.time.LocalDateTime
import java.util.*

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentViewModel = hiltViewModel(),
) {
    val title = remember { mutableStateOf("") }
    val amount = remember { mutableStateOf("") }

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
//        CategoryListDropdown(
//            viewState = viewState,
//            category = category
//        )
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
            Spacer(modifier = Modifier.width(10.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        Spacer(modifier = Modifier.height(10.dp))
        Button(
            enabled = true,
            onClick = {
                viewModel.savePayment(
                    Payment(
                        title = title.value,
                        categoryId = 1,
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

//@Composable
//private fun CategoryListDropdown(
//    viewState: PaymentViewState,
//    category: MutableState<String>
//) {
//    var expanded by remember { mutableStateOf(false) }
//    val icon = if (expanded) {
//        Icons.Filled.ArrowDropUp // requires androidx.compose.material:material-icons-extended dependency
//    } else {
//        Icons.Filled.ArrowDropDown
//    }
//
//    Column {
//        OutlinedTextField(
//            value = category.value,
//            onValueChange = { category.value = it},
//            modifier = Modifier.fillMaxWidth(),
//            label = { Text("Category") },
//            readOnly = true,
//            trailingIcon = {
//                Icon(
//                    imageVector = icon,
//                    contentDescription = null,
//                    modifier = Modifier.clickable { expanded = !expanded }
//                )
//            }
//        )
//        DropdownMenu(
//            expanded = expanded,
//            onDismissRequest = { expanded = false },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            viewState.categories.forEach { dropDownOption ->
//                DropdownMenuItem(
//                    onClick = {
//                        category.value = dropDownOption.name
//                        expanded = false
//                    }
//                ) {
//                    Text(text = dropDownOption.name)
//                }
//
//            }
//        }
//    }
//}