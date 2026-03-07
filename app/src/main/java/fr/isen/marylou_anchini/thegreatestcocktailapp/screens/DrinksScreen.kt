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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import fr.isen.marylou_anchini.thegreatestcocktailapp.DetailCocktailActivity
import fr.isen.marylou_anchini.thegreatestcocktailapp.R
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.DrinkFilterResponse
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.DrinkPreview
import fr.isen.marylou_anchini.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun DrinksScreen(modifier: Modifier, category: String) {

    var drinks = remember { mutableStateOf<List<DrinkPreview>?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        val call = ApiClient.retrofit.getDrinksPreview(category)
        call.enqueue(object : retrofit2.Callback<DrinkFilterResponse> {
            override fun onResponse(
                call: Call<DrinkFilterResponse?>?,
                response: Response<DrinkFilterResponse?>?
            ) {
                drinks.value = response?.body()?.drinks
            }

            override fun onFailure(
                call: Call<DrinkFilterResponse?>?,
                t: Throwable?
            ) {
                TODO("Not yet implemented")
            }
        })
    }

    drinks.value?.let { drinks ->
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
        LazyColumn(modifier
            .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(drinks){ drink ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DetailCocktailActivity::class.java)
                    intent.putExtra(DetailCocktailActivity.DRINKID, drink.idDrink)
                    intent.putExtra(DetailCocktailActivity.SCREEN_TITLE, category)
                    context.startActivity(intent)
                }, colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.dark_pink)))
                {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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