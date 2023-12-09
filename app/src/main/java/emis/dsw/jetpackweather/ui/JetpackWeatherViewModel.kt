package emis.dsw.jetpackweather.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject

data class DiceUiState(
    val currentIndex: Int = 0,
    val currentTemp: Any = 999.0f,
    val currentWLocation: WLocation = WLocation("Wrocław", 51.13f, 16.95f),
    val locations: MutableList<WLocation> = arrayListOf(
        WLocation("Wrocław", 51.13f, 16.95f),
        WLocation("Warszawa", 52.23f, 21.0f),
        WLocation("Paryż", 48.95f, 2.3f),
        WLocation("Kair", 30.06f, 31.22f),
        WLocation("Miami", 25.85f, -80.23f),
        WLocation("Madryt", 40.39f, -3.71f),
        WLocation("Nicea", 43.71f, 7.26f),
        WLocation("Berno", 47.0f, 7.41f),
        WLocation("Bangkok", 13.73f, 100.48f),
        WLocation("Manila", 14.53f, 120.48f),
        WLocation("Melbourne", -37.80f, 145.0f),
        WLocation("Władywostok", 43.12f, 131.9f),
        WLocation("Busan", 35.19f, 129.01f),
        WLocation("Tokio", 35.80f, 139.0f),
        WLocation("Taichung", 24.20f, 120.0f),
        WLocation("Makau", 22.21f, 113.5f),
        WLocation("Kuala Lumpur", 3f, 101.0f),
        WLocation("Belgrano", -77.80f, -34.0f),
        WLocation("Esperanza", -51.02f, -70.7f),
        WLocation("Charlotte", 35.24f, -80.0f),
        WLocation("Oklahoma City", 35.45f, -97.0f),
        WLocation("Tucson", 32.80f, -111.0f),
        WLocation("San Jose", 37.40f, -121.8f),
        WLocation("Lafayette", 30.10f, -92.16f),
    ),
)


class JetpackWeatherViewModel(private val weatherRepo: GetWeatherRepo = GetWeatherRepo()) :
    ViewModel() {


    // Expose screen UI state
    private val _uiState = MutableStateFlow(DiceUiState())
    val uiState: StateFlow<DiceUiState> = _uiState.asStateFlow()

    fun getWeather() {
        viewModelScope.launch {
            val response = weatherRepo.getWeatherCall(uiState.value.currentWLocation)
            val jsonObject = JSONObject(response.bodyAsText())


            println(jsonObject.getString("timezone"))
            val temp = jsonObject.getJSONObject("current").getDouble("temperature_2m")

            _uiState.update { currentState: DiceUiState ->
                currentState.copy(
                    currentTemp = temp,
                    currentWLocation = WLocation(
                        currentState.currentWLocation.name,
                        jsonObject.getString("latitude"),
                        jsonObject.getString(
                            "longitude"
                        )
                    )
                )
            }
//            _uiState.value = DiceUiState(
//                currentTemp = temp,
//                currentWLocation = "Szerokośc: " + jsonObject.getString("latitude") + " Długość: " + jsonObject.getString(
//                    "longitude"
//                )
//            )


        }
    }

    fun addLocation(list: MutableList<WLocation>) {

        println("Locations size: asd " + list.size);
//        _uiState.value = DiceUiState(
//            locations = list
//        )
        _uiState.update { currentState: DiceUiState ->
            currentState.copy(
                locations = list,
            )
        }
    }

    fun setLocation(index: Int) {
        _uiState.update { currentState: DiceUiState ->
            currentState.copy(
                locations = currentState.locations,
                currentWLocation = uiState.value.locations[index],
                currentIndex = index
            )
        }
        getWeather()
    }
}