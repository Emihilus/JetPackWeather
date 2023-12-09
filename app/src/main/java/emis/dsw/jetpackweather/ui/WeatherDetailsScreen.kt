package emis.dsw.jetpackweather.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherDetailsScreen(modifier: Modifier = Modifier, saveLocation: (String, Any, Any) -> Unit) {


    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Text(
            modifier = Modifier.padding(8.dp),
            text = "Wpisz dane nowej lokalizacji",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.secondary
        )
        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa") }
        )
        var latitude by remember { mutableStateOf("51.14") }
        TextField(
            value = latitude,
            onValueChange = { latitude = it },
            label = { Text("Szerokość") }
        )
        var longitude by remember { mutableStateOf("16.96") }
        TextField(
            value = longitude,
            onValueChange = { longitude = it },
            label = { Text("Długość") }
        )

        Button(onClick = {
            saveLocation(name, latitude, longitude)
            println("Locations ");
            name = ""
            latitude = ""
            longitude = ""
        }) {
            Text(
                text = "Dodaj",
            )
        }
    }
}