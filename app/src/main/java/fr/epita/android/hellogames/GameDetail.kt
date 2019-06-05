package fr.epita.android.hellogames

data class GameDetail(
    val id : Int,
    val name : String,
    val type : String,
    val players : Int,
    val year : Int,
    val url : String,
    val picture : String,
    val description_fr : String,
    val description_en : String
)