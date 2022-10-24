package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.Episode

data class SeasonDataVM (val _id : String?,
                         val air_date : String?,
                         var episodes : List<Episode>?,
                         val name : String?,
                         val overview: String?,
                         val id : Int?,
                         val poster_path: String?,
                         val season_number: Int ?)