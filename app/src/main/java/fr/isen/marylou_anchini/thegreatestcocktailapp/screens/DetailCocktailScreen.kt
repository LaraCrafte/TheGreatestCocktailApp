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
import androidx.compose.ui.text.style.TextAlign
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
fun DetailCocktailScreen(drinkId: String, screenTitle: String, modifier: Modifier, onComposing: (AppBarState) -> Unit) {
    var drink = remember { mutableStateOf<Drink?>(null) }

    LaunchedEffect(Unit) {
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
            AppBarState(screenTitle,
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
    val favoritesManager = FavoriteManager()
    drink?.let { drink ->
        var isFavorites = remember {
            mutableStateOf<Boolean>(favoritesManager.isFavorite(drink, context))
        }

        IconButton({
            favoritesManager.toggleFavorite(drink, context)
            isFavorites.value = favoritesManager.isFavorite(drink, context)
        }) {
            Icon(
                imageVector = if (isFavorites.value) {
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
                    )
                )
            ).fillMaxSize()
        ) {
            Column(
                modifier = modifier.fillMaxWidth().verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
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
                    text = drink.strDrink ?: "",
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 48.sp,
                    color = Color.White,
                    modifier = Modifier.fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp),
                    textAlign = TextAlign.Center
                )

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                        .padding(40.dp)
                ) {
                    CategoryView(alcoholCategory(drink.strAlcoholic))
                }

                Text(
                    text = drink.strGlass ?: "Unknown glass",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(top = 4.dp)
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

    fun alcoholCategory(alcoholic: String?): Category {
        return when (alcoholic?.trim()?.lowercase()) {
            "alcoholic" -> Category.ALCOHOLIC
            "non alcoholic" -> Category.MOCKTAILS
            "optional alcohol" -> Category.OTHER
            else -> Category.OTHER
        }
    }


    @Composable
    fun CategoryView(category: Category) {
        Box(
            modifier = Modifier.padding(horizontal = 4.dp).clip(CircleShape)
                .background(colorResource(id = R.color.dark_pink))
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
