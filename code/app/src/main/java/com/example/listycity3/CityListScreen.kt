package com.example.listycity3

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme

@Composable
fun CityListScreen(
    cities: List<City>,
    onUpdateCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedCity by remember { mutableStateOf<City?>(null) }

    LazyColumn(modifier = modifier) {
        itemsIndexed(cities) { index, city ->
            CityRow(
                city = city,
                onClick = { selectedCity = city }
            )

            if (index < cities.lastIndex) {
                HorizontalDivider()
            }
        }
    }

    selectedCity?.let { city ->
        EditCityDialog(
            city = city,
            onDismiss = { selectedCity = null },
            onSave = { updatedCity ->
                onUpdateCity(city, updatedCity)
                selectedCity = null
            }
        )
    }
}

@Composable
fun CityRow(
    city: City,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun EditCityDialog(
    city: City,
    onDismiss: () -> Unit,
    onSave: (City) -> Unit
) {
    var cityName by remember(city) { mutableStateOf(city.name) }
    var provinceName by remember(city) { mutableStateOf(city.province) }
    val canSave = cityName.isNotBlank() && provinceName.isNotBlank()

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit city") },
        text = {
            Column {
                OutlinedTextField(
                    value = cityName,
                    onValueChange = { cityName = it },
                    label = { Text("City") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = provinceName,
                    onValueChange = { provinceName = it },
                    label = { Text("Province") },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        City(
                            name = cityName.trim(),
                            province = provinceName.trim()
                        )
                    )
                },
                enabled = canSave
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),
            onUpdateCity = { _, _ -> }
        )
    }
}
