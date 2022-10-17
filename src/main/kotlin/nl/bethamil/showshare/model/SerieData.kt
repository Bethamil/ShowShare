package nl.bethamil.showshare.model

data class SerieData(
    val results: List<Show>
)

data class Show(
    val id: Int,
    val first_air_date: String = "",
    val last_air_date: String = "",
    val backdrop_path: String,
    val name: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int
)