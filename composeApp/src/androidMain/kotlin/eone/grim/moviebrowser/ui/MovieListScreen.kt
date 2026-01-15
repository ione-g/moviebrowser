package eone.grim.moviebrowser.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import eone.grim.moviebrowser.presentation.model.MovieUI
import eone.grim.moviebrowser.presentation.movies.list.MovieListContract
import eone.grim.moviebrowser.presentation.movies.list.MovieListViewModel
import eone.grim.moviebrowser.ui.nav.Route
import org.koin.compose.koinInject

@Composable
fun MovieListScreen(nav: NavHostController) {
    val vm: MovieListViewModel = koinInject()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.onIntent(MovieListContract.Intent.OnAppear) }
    LaunchedEffect(Unit) {
        vm.effects.collect { eff ->
            when (eff) {
                is MovieListContract.Effect.NavigateToDetails -> nav.navigate(Route.Details.create(eff.id))
                is MovieListContract.Effect.ShowMessage -> {  }
            }
        }
    }

    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        state.error != null -> Column(Modifier.padding(16.dp)) {
            Text("Error: ${state.error}")
            Spacer(Modifier.height(8.dp))
            Button(onClick = { vm.onIntent(MovieListContract.Intent.OnRetry) }) { Text("Retry") }
        }
        else -> LazyColumn {
            items(state.items, key = { it.id }) { movie ->
                MovieRow(
                    movie = movie,
                    onClick = { vm.onIntent(MovieListContract.Intent.OnMovieClick(movie.id)) },
                    onToggle = { vm.onIntent(MovieListContract.Intent.OnToggleFavorite(movie.id)) }
                )
            }
        }
    }
}

@Composable
private fun MovieRow(
    movie: MovieUI,
    onClick: () -> Unit,
    onToggle: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(12.dp)
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = null,
            modifier = Modifier.size(72.dp)
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier.weight(1f)) {
            Text(movie.title, style = MaterialTheme.typography.titleMedium)
            Text(
                movie.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
        IconButton(onClick = onToggle) {
            Icon(
                imageVector = if (movie.isFavorite) Icons.Filled.Star else Icons.Filled.StarBorder,
                contentDescription = null
            )
        }
    }
}
