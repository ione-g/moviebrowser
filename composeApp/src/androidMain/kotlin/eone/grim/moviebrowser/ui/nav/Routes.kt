package eone.grim.moviebrowser.ui.nav

sealed class Route(val path: String) {
    data object Movies : Route("movies")
    data object Favorites : Route("favorites")
    data object Details : Route("details/{id}") {
        fun create(id: Long) = "details/$id"
    }
}