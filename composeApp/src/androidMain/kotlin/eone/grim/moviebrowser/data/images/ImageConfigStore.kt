package eone.grim.moviebrowser.data.images

import eone.grim.moviebrowser.data.remote.TmdbApi
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class ImageConfigStore(
    private val api: TmdbApi
) {
    private val mutex = Mutex()
    private var secureBaseUrl: String? = null
    private var posterSize: String? = null

    suspend fun ensureLoaded() {
        if (secureBaseUrl != null && posterSize != null) return
        mutex.withLock {
            if (secureBaseUrl != null && posterSize != null) return@withLock
            val cfg = api.getConfiguration()
            secureBaseUrl = cfg.images.secureBaseUrl
            posterSize = cfg.images.posterSizes.firstOrNull { it == "w500" }
                ?: cfg.images.posterSizes.firstOrNull()
                        ?: "original"
        }
    }

    suspend fun posterUrl(posterPath: String?): String? {
        if (posterPath.isNullOrBlank()) return null
        ensureLoaded()
        return "${secureBaseUrl}${posterSize}$posterPath"
    }
}