package nl.bethamil.showshare.model

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



data class Episode(
        val air_date: String?,
        val episode_number: Int?,
        val id: Int?,
        val name: String?,
        val overview: String?,
        val season_number: Int?,
        val show_id: Int?,
        val still_path: String?,
        val vote_average: Double?,
    )


