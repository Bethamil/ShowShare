package nl.bethamil.showshare.viewmodel

data class SeasonDataVM (val _id : String?,
                         val air_date : String?,
                         var episodes : List<EpisodeVM>?,
                         val name : String?,
                         val overview: String?,
                         val id : Int?,
                         val poster_path: String?,
                         val season_number: Int ?)