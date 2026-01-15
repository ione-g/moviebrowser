package eone.grim.moviebrowser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import eone.grim.moviebrowser.presentation.movies.details.MovieDetailsContract
import eone.grim.moviebrowser.presentation.movies.details.MovieDetailsViewModel
import org.koin.compose.koinInject

@Composable
fun MovieDetailsScreen(id: Long) {
    val vm: MovieDetailsViewModel = koinInject()
    val state by vm.state.collectAsState()

    LaunchedEffect(id) { vm.onIntent(MovieDetailsContract.Intent.Load(id)) }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Text("Error: ${state.error}", modifier = Modifier.padding(16.dp))
        state.movie != null -> {
            val m = state.movie!!
            Column(Modifier.fillMaxSize().padding(16.dp)) {
                Text(m.title, style = MaterialTheme.typography.headlineSmall)
                Spacer(Modifier.height(8.dp))
                AsyncImage(model = m.posterUrl, contentDescription = null, modifier = Modifier.fillMaxWidth().height(260.dp))
                Spacer(Modifier.height(12.dp))
                Text("Year: ${m.releaseDate ?: "-"}")
                Spacer(Modifier.height(8.dp))
                Text(m.overview)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { vm.onIntent(MovieDetailsContract.Intent.ToggleFavorite) }) {
                    Text(if (m.isFavorite) "Remove from favorites" else "Add to favorites")
                }
            }
        }
    }
}


