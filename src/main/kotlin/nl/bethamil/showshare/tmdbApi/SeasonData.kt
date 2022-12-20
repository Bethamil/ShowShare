package nl.bethamil.showshare.model

import nl.bethamil.showshare.tmdbApi.Episode

data class SeasonData(
    val _id: String?,
    val air_date: String?,
    var episodes: List<Episode>?,
    val name: String?,
    val overview: String?,
    val id: Int?,
    val poster_path: String?,
    val season_number: Int?
) {init {
    if (episodes == null) {
        episodes = emptyList()
    }
}


    constructor() : this(
        null, null, emptyList(), null,
        null, null, null, null
    )
}


