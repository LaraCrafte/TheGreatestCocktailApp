package fr.isen.marylou_anchini.thegreatestcocktailapp.screens

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import coil3.compose.AsyncImage
import fr.isen.marylou_anchini.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.marylou_anchini.thegreatestcocktailapp.managers.FavoriteManager
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import fr.isen.marylou_anchini.thegreatestcocktailapp.R

@Composable
fun FavoritesScreen(modifier: Modifier, onComposing: (AppBarState) -> Unit)
{
    val context = LocalContext.current
    val favoriteManager = FavoriteManager()
    val favorites = remember { mutableStateOf(favoriteManager.getFavorites(context)) }
    val lifecycleOwner = LocalLifecycleOwner.current
    val lifeCycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()



    LaunchedEffect(lifeCycleState) {
        onComposing(
            AppBarState("Favorites")
        )

        when(lifeCycleState) {
            Lifecycle.State.RESUMED -> {
                favorites.value = favoriteManager.getFavorites(context)
            }
            else -> { }
        }
    }



    favorites.value.let { list ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            colorResource(id = R.color.orange),
                            colorResource(id = R.color.pink)
                        )
                    )
                )
        )
        LazyColumn(
            modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(list) { drink ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DetailCocktailActivity::class.java)
                    intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                    intent.putExtra(DetailCocktailActivity.SCREEN_TITLE, "Favorites")
                    context.startActivity(intent)
                },  colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.dark_pink)))
                {
                    Row (verticalAlignment = Alignment.CenterVertically)
                    {
                        AsyncImage(
                            model = drink.strDrinkThumb,
                            "",
                            Modifier.width(80.dp)
                                .height(80.dp)
                                .clip(CircleShape)
                        )
                        Text(
                            text = "${drink.strDrink}",
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}