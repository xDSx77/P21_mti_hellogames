package fr.epita.android.hellogames

import retrofit2.http.Url

data class Games(
    val id : Int,
    val name : String,
    val picture : Url
)