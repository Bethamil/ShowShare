package nl.bethamil.showshare.model

import nl.bethamil.showshare.tmdbApi.Season

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

    fun getGenresAsString(): String {
        var genreString = ""
        if (genres!!.isNotEmpty()) {
            genres?.forEach { g -> genreString += g.name + ", " }
            genreString = genreString.removeRange(genreString.length - 2, genreString.length)
        }
        return genreString
    }



    constructor() : this(
        null, null, null, null, null, null,
        null, null, null, null, null, null, emptyList(), emptyList()
    )


}



data class Genre(
    val id: Int,
    val name: String
)