package emis.dsw.jetpackweather.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun WeatherLocationsGridScreen(
    modifier: Modifier = Modifier,
    locations: MutableList<WLocation>,
    setLocation: (Int) -> Unit,
    currentIndex: Int
) {
    Column(
        modifier = modifier.fillMaxSize(),
    )
    {

        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
            items(locations.size) { index ->

                var color =
                    if (currentIndex == index) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                Column(modifier = Modifier
                    .clickable(
                        onClick = { setLocation(index) }
                    )
                    .background(MaterialTheme.colorScheme.scrim)) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = AnnotatedString(locations[index].name + " \n(" + locations[index].latitude + " , " + locations[index].longitude + ")"),
                        style = MaterialTheme.typography.titleLarge,
                        color = color
                    )
                }
            }
        }
    }
}