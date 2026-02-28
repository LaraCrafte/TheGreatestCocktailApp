package fr.isen.marylou_anchini.thegreatestcocktailapp.dataclasses

import com.google.gson.annotations.SerializedName

data class DrinkCategory(
    @SerializedName("strCategory")
    val strCategory: String?
)