@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.sano.ideallist.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.sano.ideallist.datapersistance.FilterSettings
import com.sano.ideallist.viewModel.MonstersListViewModel

@Composable
fun FiltersScreen(monstersListViewModel: MonstersListViewModel) {
    val filterSettings by monstersListViewModel.filterSettings.observeAsState()

    var currentFilterSettings by remember {
        mutableStateOf(
            filterSettings?.copy() ?: FilterSettings()
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxHeight()
            .padding(10.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            TextField(
                value = currentFilterSettings.name,
                label = {
                    Text(
                        "Name",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                onValueChange = {
                    currentFilterSettings = currentFilterSettings.copy(name = it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
            DropDownAlignment(
                currentAlignmentSetting = currentFilterSettings.alignment,
                possibleValues = listOf("Any", "Good", "Evil"),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            ) { currentFilterSettings = currentFilterSettings.copy(alignment = it) }
            TextField(
                value = if (currentFilterSettings.abilitiesCount >= 0) currentFilterSettings.abilitiesCount.toString() else "",
                label = {
                    Text(
                        "Abilities count",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    )
                },
                textStyle = MaterialTheme.typography.bodyMedium,
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    currentFilterSettings =
                        currentFilterSettings.copy(abilitiesCount = it.toIntOrNull() ?: -1)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp)
            )
        }
        Row {
            Button(
                onClick = {
                    monstersListViewModel.saveFilterSettings(currentFilterSettings)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Apply")
            }
            Button(
                onClick = {
                    currentFilterSettings = FilterSettings()
                    monstersListViewModel.saveFilterSettings(currentFilterSettings)
                },
                modifier = Modifier.weight(1f)
            ) {
                Text("Clear")
            }
        }
    }
}

@Composable
fun DropDownAlignment(
    currentAlignmentSetting: String,
    possibleValues: List<String>,
    modifier: Modifier,
    onValueChange: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }) {
            TextField(
                value = currentAlignmentSetting,
                textStyle = MaterialTheme.typography.bodyMedium,
                label = {
                    Text(
                        "Alignment",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                    )
                },
                onValueChange = {},
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = modifier.menuAnchor()
            )

            ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                possibleValues.forEach { item ->
                    DropdownMenuItem(text = { Text(text = item) }, onClick = {
                        expanded = false
                        onValueChange(item)
                    })
                }
            }
        }
    }
}