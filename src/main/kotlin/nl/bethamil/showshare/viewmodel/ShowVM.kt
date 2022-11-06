package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.Genre
import nl.bethamil.showshare.model.Season

data class ShowVM(
    var id: Int? = null,
    var first_air_date: String?,
    var last_air_date: String?,
    var backdrop_path: String?,
    var name: String?,
    var original_name: String?,
    var overview: String?,
    var popularity: Double?,
    var poster_path: String?,
    var vote_average: Double?,
    var vote_count: Int?,
    var homepage: String?,
    var seasons: List<Season>?,
    var genres: List<Genre>?
)
{


    fun getGenresAsString(): String {
        var genreString = ""
        if (genres!!.isNotEmpty()) {
            genres?.forEach { g -> genreString += g.name + ", " }
            genreString = genreString.removeRange(genreString.length - 2, genreString.length)
        }
        return genreString
    }
}
