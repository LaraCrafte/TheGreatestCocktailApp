package fr.isen.marylou_anchini.thegreatestcocktailapp.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.isen.marylou_anchini.thegreatestcocktailapp.R
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.Category
import fr.isen.marylou_anchini.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response
import coil3.compose.AsyncImage
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.CocktailResponse
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.Drink
import fr.isen.marylou_anchini.thegreatestcocktailapp.managers.FavoriteManager
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState


@Composable
fun RandomCocktailScreen(modifier: Modifier, onComposing: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {
//        drink.value = ApiClient.retrofit.getRandom().drinks?.first()
        val call = ApiClient.retrofit.getRandomCocktail()
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })
        onComposing(
            AppBarState("Random Cocktails",
                   actions = { DetailCocktailTopButton(drink.value) })
        )
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink)
    } ?: run {
        Text("Loading")
    }
}


@Composable
fun DetailCocktailScreen(drinkId: String, modifier: Modifier, onComposing: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {
//        drink.value = ApiClient.retrofit.getRandom().drinks?.first()
        val call = ApiClient.retrofit.getDetailCocktail(drinkId)
        call.enqueue(object : retrofit2.Callback<CocktailResponse> {
            override fun onResponse(
                call: Call<CocktailResponse?>?,
                response: Response<CocktailResponse?>?
            ) {
                drink.value = response?.body()?.drinks?.first()
            }
            override fun onFailure(
                call: Call<CocktailResponse?>?,
                t: Throwable?
            ) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })

        onComposing(
            AppBarState("Random Cocktails",
                actions = { DetailCocktailTopButton(drink.value) })
        )
    }

    drink.value?.let { drink ->
        DetailCocktailScreen(modifier, drink)
    } ?: run {
        Text("Loading")
    }
}

@Composable
fun DetailCocktailTopButton(drink: Drink?) {
    val context = LocalContext.current
    val favoriteManager = FavoriteManager()
    drink?.let { drink ->
        IconButton(onClick = {
            favoriteManager.toggleFavorite(drink, context)
        }) {
            Icon(
                imageVector = if (favoriteManager.isFavorite(drink)) {
                    Icons.Filled.Favorite
                } else {
                    Icons.Filled.FavoriteBorder
                },
                contentDescription = "Localized description"
            )

        }
    }
}

    @Composable
    fun DetailCocktailScreen(modifier: Modifier, drink: Drink) {
        Box(
            Modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        colorResource(id = R.color.orange),
                        colorResource(id = R.color.pink),
                        //colorResource(id = R.color.purple_700)
                    )
                )
            ).fillMaxSize()
        ) {
            Column(
                modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                //verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                AsyncImage(
                    model = drink.strDrinkThumb,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .width(250.dp)
                        .height(250.dp)
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            colorResource(R.color.white),
                            CircleShape
                        )
                )

                Text(
                    drink.strDrink ?: "",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                    //color = colorResource(R.color.white)
                )

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = modifier.fillMaxWidth()
                ) {
                    CategoryView(Category.OTHER)
                    CategoryView(Category.MOCKTAILS)
                }

                Text(
                    text = "Highball Glass"
                )


                val ingredients = drink.ingredientList()

                Card(Modifier.padding(15.dp)) {
                    Column(
                        Modifier.padding(16.dp).fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.ingrendient),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        if (ingredients.isEmpty()) {
                            Text("No ingredients found")
                        } else {
                            ingredients.forEach { (ingredient, measure) ->
                                val line = (measure + " " + ingredient).trim()
                                Text("• $line")
                            }
                        }
                    }
                }

                Card(Modifier.padding(15.dp)) {
                    Column(
                        Modifier.padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            stringResource(R.string.preparation),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            drink.strInstructions?.trim().orEmpty()
                                .ifEmpty { "No instructions found" })
                    }
                }

            }
        }
    }

    @Composable
    fun CategoryView(category: Category) {
        //LIGNES DES COULEURS A REVOIR
        Box(
            modifier = Modifier.padding(horizontal = 15.dp).clip(CircleShape)
                .background(colorResource(id = R.color.light_green))
        )
        {
            Text(
                Category.toString(category),
                fontSize = 18.sp,
                color = colorResource(id = R.color.white),
                modifier = Modifier.padding(10.dp)
            )
        }
    }
