package fr.isen.marylou_anchini.thegreatestcocktailapp.screens

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import fr.isen.marylou_anchini.thegreatestcocktailapp.DrinksActivity
import fr.isen.marylou_anchini.thegreatestcocktailapp.R
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.CategoryListResponse
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.DrinkCategory
import fr.isen.marylou_anchini.thegreatestcocktailapp.models.AppBarState
import fr.isen.marylou_anchini.thegreatestcocktailapp.network.ApiClient
import retrofit2.Call
import retrofit2.Response

@Composable
fun CategoriesScreen(modifier: Modifier,onComposing: (AppBarState) -> Unit) {
    //val list = listOf( "Beer", "Cocktail", "Coffee")
    val context = LocalContext.current
    var categories = remember { mutableStateOf<List<DrinkCategory>?>(null) }

    LaunchedEffect(Unit) {
        val call = ApiClient.retrofit.getCategories()
        call.enqueue(object : retrofit2.Callback<CategoryListResponse> {
            override fun onResponse(
                call: Call<CategoryListResponse>,
                response: Response<CategoryListResponse>
            ) {
                categories.value = response?.body()?.drinks
            }

            override fun onFailure(call: Call<CategoryListResponse>, t: Throwable) {
                Log.e("request", "getrandom failed ${t?.message}")
            }
        })
        onComposing(
            AppBarState("Categories")
        )
    }
    categories.value?.let { list ->
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
            .padding(
                start = 16.dp,
                end = 16.dp,
                top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list){ category ->
                Card(Modifier.clickable {
                    val intent = Intent(context, DrinksActivity::class.java)
                    intent.putExtra(DrinksActivity.CATEGORY, category.strCategory)
                    context.startActivity(intent)
                },  colors = CardDefaults.cardColors(
                    containerColor = colorResource(id = R.color.dark_pink)) )
                {
                    Text("${category.strCategory}",
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth())
                }
            }
        }
    }
}