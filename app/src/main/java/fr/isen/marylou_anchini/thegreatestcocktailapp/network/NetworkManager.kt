package fr.isen.marylou_anchini.thegreatestcocktailapp.network

import fr.isen.marylou_anchini.thegreatestcocktailapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://www.thecocktaildb.com/api/json/v1/1/"

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}