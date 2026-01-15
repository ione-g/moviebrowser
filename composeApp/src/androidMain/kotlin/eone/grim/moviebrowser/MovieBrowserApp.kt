package eone.grim.moviebrowser

import android.app.Application
import eone.grim.moviebrowser.di.appModules
import org.koin.core.context.startKoin

class MovieBrowserApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(appModules(this@MovieBrowserApp))
        }
    }
}