package nl.bethamil.showshare.model

data class SerieData(
    val results: List<Show> = emptyList()
)

data class Show(
    val id: Int? = null,
    val first_air_date: String?,
    val last_air_date: String?,
    val backdrop_path: String?,
    val name: String?,
    val original_name: String?,
    val overview: String?,
    val popularity: Double?,
    val poster_path: String?,
    val vote_average: Double?,
    val vote_count: Int?,
    val homepage: String?,
    var seasons: List<Season>?,
    var genres: List<Genre>?
) {
    init {
        if (seasons == null) {
            seasons = emptyList()
        }
        if (genres == null) {
            genres = emptyList()
        }
    }



    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null, null, emptyList(), emptyList()
    )


}

data class Season(
    val air_date: String?,
    val episode_count: Int?,
    val name: String?,
    val overview: String?,
    val season_number: Int?,
    val poster_path: String?,
)

data class Genre(
    val id: Int,
    val name: String
)