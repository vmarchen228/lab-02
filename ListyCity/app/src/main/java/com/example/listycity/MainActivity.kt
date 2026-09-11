package com.example.listycity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity.ui.theme.ListyCityTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = {cityRepository.addCity(it )},
                        onRemoveCity = {cityRepository.removeCity(it)},
                        modifier = Modifier.padding(paddingValues = innerPadding))
                }
            }
        }
    }
}
@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    modifier: Modifier = Modifier) {
    var newCityName by remember {mutableStateOf (value = "") }
    var selectedCity by remember {mutableStateOf (value = "") }
    Column(modifier = modifier.fillMaxSize()) {
        Row() {
            OutlinedTextField(
                value = newCityName,
                onValueChange = { newCityName = it},
                label =  { Text ("City name")},
                modifier = Modifier.weight(1f)

            )


            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (newCityName.isNotBlank()) {
                    onAddCity(newCityName)
                    newCityName = ""
                }
            }) { Text("Add City") }
            Button(
                onClick = {
                if (selectedCity.isNotBlank()) {
                    onRemoveCity(selectedCity)
                    selectedCity = ""
                }



            },
                enabled = selectedCity.isNotBlank()
            ) {
                Text("Remove City") }
        }

        LazyColumn(modifier = modifier.fillMaxSize()) {
            items(cities) { city ->
                val isSelected = (city == selectedCity)
                CityRow(city = city,
                    isSelected = isSelected,
                    onClick = {
                        selectedCity = if (isSelected) "" else city
                    })

            }
        }
    }

}
@Composable
fun CityRow(city: String, isSelected: Boolean,
            onClick: () -> Unit){
    Text (
        text = city,
        fontSize= 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .clickable { onClick() }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ListyCityTheme {
        Greeting("Android")
    }
}

class CityRepository {
    private val _cities = mutableStateListOf("edmonton","vancouver", "kyiv","sydney",
        "berlin","vienna", "tokyo","beijing")
    val cities : List<String>
        get() = _cities

    fun addCity(city: String){
        _cities.add(city)
    }
    fun removeCity(city : String){
        _cities.remove(city)

    }


}