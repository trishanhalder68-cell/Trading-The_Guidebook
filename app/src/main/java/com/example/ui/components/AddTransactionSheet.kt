package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.RecurrencePeriod
import com.example.data.model.TransactionEntity
import com.example.data.model.TransactionType
import com.example.ui.theme.CrimsonLoss
import com.example.ui.theme.EmeraldProfit
import com.example.ui.theme.GoldBorder
import com.example.ui.theme.GoldGleam
import com.example.ui.theme.ImperialGold
import com.example.ui.theme.ObsidianBlack
import com.example.ui.theme.ObsidianCardElevated
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.theme.TextTertiary

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AddTransactionSheet(
    currencySymbol: String,
    onDismiss: () -> Unit,
    onSaveTransaction: (TransactionEntity) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var type by remember { mutableStateOf(TransactionType.INCOME) }
    var title by remember { mutableStateOf("") }
    var amountText by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var recurrence by remember { mutableStateOf(RecurrencePeriod.DAILY) }

    val incomeCategories = listOf("Salary", "Freelance", "Trading", "Dividends", "Business", "Bonus", "Other")
    val expenseCategories = listOf("Investment", "Food", "Housing", "Utilities", "Transport", "Lifestyle", "Healthcare", "Other")

    var selectedCategory by remember { mutableStateOf(incomeCategories.first()) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = ObsidianBlack,
        shape = androidx.compose.foundation.shape.CutCornerShape(topStart = 20.dp, topEnd = 20.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 22.dp)
                .padding(bottom = 32.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Record Cashflow Entry",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextPrimary
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = onDismiss) {
                    Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextTertiary)
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Income vs Expense Segmented Switcher
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(ObsidianCardElevated)
                    .border(1.dp, GoldBorder, RoundedCornerShape(16.dp))
                    .padding(4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (type == TransactionType.INCOME) EmeraldProfit else Color.Transparent)
                        .clickable {
                            type = TransactionType.INCOME
                            selectedCategory = incomeCategories.first()
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ArrowUpward,
                            contentDescription = null,
                            tint = if (type == TransactionType.INCOME) ObsidianBlack else EmeraldProfit,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Income Inflow",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (type == TransactionType.INCOME) ObsidianBlack else TextPrimary
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (type == TransactionType.EXPENSE) CrimsonLoss else Color.Transparent)
                        .clickable {
                            type = TransactionType.EXPENSE
                            selectedCategory = expenseCategories.first()
                        }
                        .padding(vertical = 12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ArrowDownward,
                            contentDescription = null,
                            tint = if (type == TransactionType.EXPENSE) Color.White else CrimsonLoss,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Expense Outflow",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (type == TransactionType.EXPENSE) Color.White else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Amount Input
            OutlinedTextField(
                value = amountText,
                onValueChange = { amountText = it },
                label = { Text("Amount ($currencySymbol)") },
                prefix = { Text("$currencySymbol ", color = ImperialGold, fontWeight = FontWeight.Bold) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ImperialGold,
                    unfocusedBorderColor = GoldBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Quick Amount Adder Chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf(500, 2000, 10000, 50000).forEach { quickVal ->
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(ObsidianCardElevated)
                            .border(1.dp, GoldBorder.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
                            .clickable {
                                val current = amountText.toDoubleOrNull() ?: 0.0
                                amountText = (current + quickVal).toInt().toString()
                            }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "+$currencySymbol$quickVal",
                            fontSize = 12.sp,
                            color = GoldGleam,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title / Description") },
                placeholder = { Text("e.g. Day Trade Profit, Tech Salary, Nifty SIP") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ImperialGold,
                    unfocusedBorderColor = GoldBorder,
                    focusedTextColor = TextPrimary,
                    unfocusedTextColor = TextPrimary
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Recurrence Frequency
            Text("Recurrence Schedule", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                RecurrencePeriod.entries.forEach { period ->
                    val isSelected = recurrence == period
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isSelected) ImperialGold.copy(alpha = 0.2f) else ObsidianCardElevated)
                            .border(1.dp, if (isSelected) ImperialGold else GoldBorder, RoundedCornerShape(10.dp))
                            .clickable { recurrence = period }
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = period.name,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) ImperialGold else TextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Category Chips
            Text("Select Category", fontSize = 13.sp, color = TextSecondary, fontWeight = FontWeight.Medium)
            Spacer(modifier = Modifier.height(8.dp))
            val currentCategories = if (type == TransactionType.INCOME) incomeCategories else expenseCategories
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                currentCategories.forEach { cat ->
                    val isSelected = selectedCategory == cat
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) ImperialGold else ObsidianCardElevated)
                            .border(1.dp, if (isSelected) ImperialGold else GoldBorder, RoundedCornerShape(12.dp))
                            .clickable { selectedCategory = cat }
                            .padding(horizontal = 14.dp, vertical = 8.dp)
                    ) {
                        Text(
                            text = cat,
                            fontSize = 12.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) ObsidianBlack else TextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Button
            Button(
                onClick = {
                    val parsedAmount = amountText.toDoubleOrNull() ?: 0.0
                    if (parsedAmount > 0) {
                        val finalTitle = title.ifBlank {
                            if (type == TransactionType.INCOME) "Income: $selectedCategory" else "Expense: $selectedCategory"
                        }
                        onSaveTransaction(
                            TransactionEntity(
                                title = finalTitle,
                                amount = parsedAmount,
                                type = type,
                                category = selectedCategory,
                                recurrence = recurrence,
                                note = note
                            )
                        )
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ImperialGold,
                    contentColor = ObsidianBlack
                ),
                enabled = (amountText.toDoubleOrNull() ?: 0.0) > 0
            ) {
                Icon(imageVector = Icons.Default.AddCircle, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Confirm & Record to Vault", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}
