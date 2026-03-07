package fr.isen.marylou_anchini.thegreatestcocktailapp.managers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses.Drink

data class Favorites (
    @SerializedName(value = "favorites")
    var favorites: MutableList<Drink> = mutableListOf()
)

class FavoriteManager {
    fun toggleFavorite(drink: Drink, context: Context) {
        val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = prefs.getString("list", null)
        val favors = if (json != null) Gson().fromJson(json, Favorites::class.java) else Favorites()

        val already = favors.favorites.any { it.idDrink == drink.idDrink }

        if (already) {
            favors.favorites.removeAll { it.idDrink == drink.idDrink }
        } else {
            favors.favorites.add(drink)
        }

        prefs.edit().putString("list", Gson().toJson(favors)).apply()
    }

    fun isFavorite(drink: Drink, context: Context): Boolean {
        val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = prefs.getString("list", null) ?: return false
        val favors = Gson().fromJson(json, Favorites::class.java)

        val already = favors.favorites.any { it.idDrink == drink.idDrink }

        return already
    }

    fun getFavorites(context: Context): List<Drink> {
        val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
        val json = prefs.getString("list", null)
        val favors = if (json != null) Gson().fromJson(json, Favorites::class.java) else Favorites()
        return favors.favorites
    }
}