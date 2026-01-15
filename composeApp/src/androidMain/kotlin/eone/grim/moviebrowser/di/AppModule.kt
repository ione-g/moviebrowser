package eone.grim.moviebrowser.di;

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import eone.grim.moviebrowser.BuildConfig
import eone.grim.moviebrowser.data.db.AppDatabase
import eone.grim.moviebrowser.data.images.ImageConfigStore
import eone.grim.moviebrowser.data.remote.TmdbApi
import eone.grim.moviebrowser.data.repository.FavoriteMoviesRepositoryImpl
import eone.grim.moviebrowser.data.repository.MovieRepositoryImpl
import eone.grim.moviebrowser.domain.repository.FavoriteMoviesRepository
import eone.grim.moviebrowser.domain.repository.MovieRepository
import eone.grim.moviebrowser.domain.usecase.GetMovieDetails
import eone.grim.moviebrowser.domain.usecase.GetPopularMovies
import eone.grim.moviebrowser.domain.usecase.ObserveFavoriteIds
import eone.grim.moviebrowser.domain.usecase.ObserveFavorites
import eone.grim.moviebrowser.domain.usecase.RemoveFavorite
import eone.grim.moviebrowser.domain.usecase.ToggleFavorite
import eone.grim.moviebrowser.network.provideHttpClient
import eone.grim.moviebrowser.presentation.movies.details.MovieDetailsViewModel
import eone.grim.moviebrowser.presentation.movies.favorites.FavoritesViewModel
import eone.grim.moviebrowser.presentation.movies.list.MovieListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.core.module.Module
import org.koin.dsl.module

fun appModules(context: Context): List<Module> = listOf(

    module {
        // App scope (MVI VM scope)
        single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate) }

        // SQLDelight
        single {
            val driver = AndroidSqliteDriver(AppDatabase.Schema, context, "app.db")
            AppDatabase(driver)
        }

        // Ktor + API
        single {
            provideHttpClient(
                baseUrl = BuildConfig.TMDB_BASE_URL,
                token = BuildConfig.TMDB_TOKEN
            )
        }
        single { TmdbApi(get(), BuildConfig.TMDB_BASE_URL) }
        single { ImageConfigStore(get()) }

        // Repositories
        single<MovieRepository> { MovieRepositoryImpl(get()) }
        single<FavoriteMoviesRepository> { FavoriteMoviesRepositoryImpl(get()) }

        // UseCases
        factory { GetPopularMovies(get()) }
        factory { GetMovieDetails(get()) }
        factory { ObserveFavorites(get()) }
        factory { ObserveFavoriteIds(get()) }
        factory { ToggleFavorite(get()) }
        factory { RemoveFavorite(get()) }

        // VMs (factory, бо це не androidx ViewModel)
        factory { MovieListViewModel(get(), get(), get(), get(), get()) }
        factory { MovieDetailsViewModel(get(), get(), get(), get(), get()) }
        factory { FavoritesViewModel(get(), get(), get(), get()) }
    }
)