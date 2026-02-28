package fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses

import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("drinks")
    val drinks: List<Drink>?
)

data class CategoryListResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkCategory>?
)

data class DrinkFilterResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkPreview>?
)