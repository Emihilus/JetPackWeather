package emis.dsw.jetpackweather.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun WeatherLocationsListScreen(
    modifier: Modifier = Modifier,
    locations: MutableList<WLocation>,
    setLocation: (Int) -> Unit,
    currentIndex: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
    )
    {


        LazyColumn() {
            items(locations.size) { index ->
//                var active = if (currentIndex === index) " Aktywny" else ""
                var color = if (currentIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                Column(modifier = Modifier.clickable(
                    onClick = { setLocation(index) }
                )) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = AnnotatedString(locations[index].name + "(" + locations[index].latitude + " , " + locations[index].longitude + ")"),
                        style = MaterialTheme.typography.titleLarge,
                        color = color
                    )
                }

            }
        }
    }
}