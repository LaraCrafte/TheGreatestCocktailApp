package fr.isen.marylou_anchini.thegreatestcocktailapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState
import fr.isen.marylou_anchini.thegreatestcocktailapp.screens.BottomAppBar
import fr.isen.marylou_anchini.thegreatestcocktailapp.screens.CategoriesScreen
import fr.isen.marylou_anchini.thegreatestcocktailapp.screens.RandomCocktailScreen
import fr.isen.marylou_anchini.thegreatestcocktailapp.screens.FavoritesScreen
import fr.isen.marylou_anchini.thegreatestcocktailapp.ui.theme.TheGreatestCocktailAppTheme

data class TabBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("LifeCycle", "MainActivity onCreate")
        setContent {
            val context = LocalContext.current
            val navController = rememberNavController()

            val appBarState = remember { mutableStateOf(AppBarState()) }

            val randomItem = TabBarItem(
                stringResource(R.string.tab_item_random),
                Icons.Filled.Home,
                Icons.Outlined.Home
            )
            val categoryItem = TabBarItem(
                stringResource(R.string.tab_item_category),
                Icons.Filled.Menu,
                Icons.Outlined.Menu
            )
            val favoriteItem = TabBarItem(
                stringResource(R.string.tab_item_favorite),
                Icons.Filled.Favorite,
                Icons.Outlined.Favorite
            )
            val tabItems = listOf(randomItem, categoryItem, favoriteItem)


            TheGreatestCocktailAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar({
                            Text(appBarState.value.title)
                            //Text("Detail")
                        }, actions = {
                            appBarState.value.actions?.invoke(this)
                        })
                    },
                    bottomBar = { BottomAppBar(tabItems, navController) }
                ) { innerPadding ->
                    NavHost(navController, startDestination = randomItem.title) {
                        composable(randomItem.title) {
                            RandomCocktailScreen(
                                Modifier.padding(innerPadding),
                                { topBar ->
                                    appBarState.value = topBar
                                })
                        }
                        composable(categoryItem.title) {
                            CategoriesScreen(
                                Modifier.padding(innerPadding),
                                { topBar ->
                                    appBarState.value = topBar
                                })
                        }
                        composable(favoriteItem.title) {
                            FavoritesScreen(
                                Modifier.padding(innerPadding),
                                { topBar ->
                                appBarState.value = topBar
                                })
                        }
                    }
                }
            }
        }
    }

    override fun onPause(){
        super.onPause()
        Log.d("LifeCycle", "MainActivity onPause")
    }

    override fun onResume(){
        super.onResume()
        Log.d("LifeCycle", "MainActivity onResume")
    }

    override fun onStop(){
        super.onStop()
        Log.d("LifeCycle", "MainActivity onStop")
    }

    override fun onStart(){
        super.onStart()
        Log.d("LifeCycle", "MainActivity onStart")
    }

    override fun onRestart(){
        super.onRestart()
        Log.d("LifeCycle", "MainActivity onRestart")
    }
}