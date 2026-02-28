package fr.isen.marylou_anchini.thegreatestcocktailapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState
import fr.isen.marylou_anchini.thegreatestcocktailapp.screens.DetailCocktailScreen
import fr.isen.marylou_anchini.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

class DetailCocktailActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drinkId = intent.getStringExtra(DRINKID) ?: ""
        enableEdgeToEdge()
        setContent {
            val appBarState = remember { mutableStateOf(AppBarState()) }
            TheGreatestCocktailAppTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            { Text(appBarState.value.title) },
                            actions = { appBarState.value.actions?.invoke(this) }
                        )
                    },
                    modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DetailCocktailScreen(drinkId, Modifier.padding(innerPadding), { topBar ->
                        appBarState.value = topBar
                    })
                }
            }
        }
    }

    companion object {
        const val DRINKID = "drinkID"
    }
}