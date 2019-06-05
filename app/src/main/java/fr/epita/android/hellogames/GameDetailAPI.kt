package fr.epita.android.hellogames

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GameDetailAPI {
    @GET("game/details")
    fun listGameDetail(@Query("game_id") game_id : Int) : Call<GameDetail>
}