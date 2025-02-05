package com.venturo.venturoreport.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.LightGray400
import com.venturo.venturoreport.ui.theme.Primary400
import com.venturo.venturoreport.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedDatePicker(
    modifier: Modifier = Modifier,
    onDatePicked: (Pair<Long, Long>) -> Unit
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDate: String? by remember { mutableStateOf(null) }
    val dateRangePickerState = rememberDateRangePickerState()

    Box(
        modifier = modifier
            .height(36.dp)
            .clip(shape = RoundedCornerShape(32.dp))
            .clickable { showDatePicker = !showDatePicker }
            .border(width = 1.dp, color = Primary400, shape = RoundedCornerShape(36.dp))
            .padding(vertical = 10.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = selectedDate ?: DateFormatter.getCurrentDate(),
                overflow = TextOverflow.Ellipsis,
                style = TextStyle(
                    color = if (selectedDate != null) Color.Black else LightGray400,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal
                )
            )
            Spacer(modifier = Modifier.width(12.dp))
            Icon(
                modifier = Modifier.size(18.dp),
                imageVector = Icons.Default.DateRange,
                contentDescription = "calendar icon",
                tint = Primary400
            )
        }
        if (showDatePicker) {
            DatePickerDialog(
                colors = DatePickerDefaults.colors(containerColor = Color.White),
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            val selectedStartDateInMillis = dateRangePickerState.selectedStartDateMillis
                            val selectedEndDateInMillis = dateRangePickerState.selectedEndDateMillis
                            if (selectedStartDateInMillis != null && selectedEndDateInMillis != null) {
                                val dateFormat = "yyyy-MM-dd" // Specify your date format here
                                selectedDate = "${DateFormatter.millisecondsToDate(selectedStartDateInMillis, dateFormat)} - ${DateFormatter.millisecondsToDate(selectedEndDateInMillis, dateFormat)}"
                                onDatePicked(Pair(selectedStartDateInMillis, selectedEndDateInMillis))
                                showDatePicker = false
                            }
                        }
                    ) {
                        Text("OK", style = TextStyle(color = Primary400))
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel", style = TextStyle(color = Primary400))
                    }
                }
            ) {
                DateRangePicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp),
                    state = dateRangePickerState,
                    showModeToggle = false,
                    colors = DatePickerDefaults.colors(
                        containerColor = Color.White,
                        selectedDayContainerColor = Primary400,
                        selectedDayContentColor = Color.White,
                        todayContentColor = Primary400,
                        selectedYearContentColor = Color.White,
                        selectedYearContainerColor = Primary400,
                        titleContentColor = Primary400,
                        headlineContentColor = Primary400,
                        todayDateBorderColor = Primary400
                    )
                )
            }
        }
    }
}