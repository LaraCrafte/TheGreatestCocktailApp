package fr.isen.marylou_anchini.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import fr.isen.marylou_anchini.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.marylou_anchini.thegreatestcocktailapp.managers.FavoriteManager
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState

@Composable
fun FavoritesScreen(modifier: Modifier, onComposing: (AppBarState) -> Unit)
{
    val context = LocalContext.current
    val favoriteManager = FavoriteManager()
    val favorites = remember { mutableStateOf(favoriteManager.getFavorites(context)) }

    LaunchedEffect(Unit) {
        onComposing(
            AppBarState("Favorites")
        )
        favorites.value = favoriteManager.getFavorites(context)
    }
    /*Column() {
        Text(
            text = "Favorite View"
        )
    }*/
    favorites.value.let { list ->
        LazyColumn(
            modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { drink ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DetailCocktailActivity::class.java)
                    intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                    context.startActivity(intent)
                }) {
                    Text(
                        text = "${drink.strDrink}",
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                }
            }
        }
    }
}