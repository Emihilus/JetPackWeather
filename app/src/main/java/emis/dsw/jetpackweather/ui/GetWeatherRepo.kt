package emis.dsw.jetpackweather.ui

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse

class GetWeatherRepo() {


//    suspend fun saveToDataStore(locations: String) {
//        dataStore.edit { preference ->
//            preference[stringPreferencesKey("locations")] = locations
//        }
//    }
//
//
//    val readFromDataStore : Flow<UserPreferences> = dataStore.data
//        .catch { exception ->
//            if (exception is IOException) {
//                Log.d("DataStoreRepository", exception.message.toString())
//                emit(emptyPreferences())
//            } else {
//                throw exception
//            }
//        }
//        .map { preference ->
//            val username = preference[USERNAME] ?: ""
//            val remember = preference[REMEMBER] ?: false
//            UserPreferences(username, remember)
//        }


    // Function that makes the network request, blocking the current thread
    suspend fun getWeatherCall(location: WLocation): HttpResponse {
        val client = HttpClient(CIO)
        val response: HttpResponse =
            client.get("https://api.open-meteo.com/v1/forecast?latitude=" + location.latitude + "&longitude=" + location.longitude + "&current=temperature_2m&timezone=Europe%2FBerlin&forecast_days=1")
        client.close()
        return response
    }

}