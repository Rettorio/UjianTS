package com.example.ujiants

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.ujiants.navigation.HomeNavigationGraph
import com.example.ujiants.navigation.LocalNavController
import com.example.ujiants.ui.theme.UjianTSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UjianTSTheme {
                val navHost = rememberNavController()
                CompositionLocalProvider(LocalNavController provides navHost) {
                    HomeNavigationGraph()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    UjianTSTheme {
        Greeting("Android")
    }
}