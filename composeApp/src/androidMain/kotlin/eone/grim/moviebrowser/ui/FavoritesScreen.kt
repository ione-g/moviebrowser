package eone.grim.moviebrowser.ui

import androidx.navigation.NavHostController

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import eone.grim.moviebrowser.presentation.movies.favorites.FavoritesContract
import eone.grim.moviebrowser.presentation.movies.favorites.FavoritesViewModel
import eone.grim.moviebrowser.ui.nav.Route
import org.koin.compose.koinInject

@Composable
fun FavoritesScreen(nav: NavHostController) {
    val vm: FavoritesViewModel = koinInject()
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) { vm.onIntent(FavoritesContract.Intent.OnAppear) }
    LaunchedEffect(Unit) {
        vm.effects.collect { eff ->
            when (eff) {
                is FavoritesContract.Effect.NavigateToDetails -> nav.navigate(Route.Details.create(eff.id))
            }
        }
    }

    if (state.isEmpty) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No favorites yet")
        }
    } else {
        LazyColumn {
            items(state.items, key = { it.id }) { movie ->
                Row(Modifier.fillMaxWidth().padding(12.dp)) {
                    AsyncImage(
                        model = movie.posterUrl,
                        contentDescription = null,
                        modifier = Modifier.size(56.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column(
                        Modifier
                            .weight(1f)
                            .clickable { vm.onIntent(FavoritesContract.Intent.OnMovieClick(movie.id)) }
                    ) {
                        Text(movie.title, style = MaterialTheme.typography.titleMedium)
                        Text(movie.overview, maxLines = 2, overflow = TextOverflow.Ellipsis)
                    }
                    IconButton(onClick = { vm.onIntent(FavoritesContract.Intent.OnRemove(movie.id)) }) {
                        Icon(Icons.Filled.Delete, contentDescription = null)
                    }
                }
            }
        }
    }
}
