package eone.grim.moviebrowser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import eone.grim.moviebrowser.ui.MainScreen
import eone.grim.moviebrowser.ui.theme.MovieBrowserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieBrowserTheme {
                Surface {
                    MainScreen()
                }
            }
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    MovieBrowserTheme {
        Surface {
            MainScreen()
        }
    }
}