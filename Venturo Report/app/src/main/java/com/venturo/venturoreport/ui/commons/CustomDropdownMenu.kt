package com.venturo.venturoreport.ui.commons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.venturo.venturoreport.ui.theme.LightGray400
import com.venturo.venturoreport.ui.theme.Primary400

data class DropdownItem(
    val title: String,
    val value: String
)



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(
    modifier: Modifier = Modifier,
    selectedOption: DropdownItem? = null,
    options: List<DropdownItem>,
    onSelect: (DropdownItem) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = modifier.height(36.dp)) {
        MaterialTheme(
            shapes = MaterialTheme.shapes.copy(
                extraSmall = RoundedCornerShape(16.dp)
            )
        ) {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor(),
                    border = BorderStroke(1.dp, color = Primary400),
                    shape = RoundedCornerShape(36.dp),
                    onClick = { expanded = !expanded }
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        text = selectedOption?.title ?: options.first().title
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "ArrowDropdown",
                        tint = LightGray400
                    )
                }
                ExposedDropdownMenu(
                    modifier = Modifier
                        .background(color = Color.White)
                        .clipToBounds(),
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    options.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.title) },
                            onClick = {
                                onSelect(item)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    }
}

