package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET

interface WebServiceAPI {
    @GET("/game/list")
    fun listGames() : Call<List<Games>>
}