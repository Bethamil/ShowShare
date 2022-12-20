package nl.bethamil.showshare.viewmodel

data class WatchedEpisodeVM(
    var viewDateId: Long? = null,
    val showShareUser: ShowShareUserVM,
    val showId: Int,
    val seasonNumber: Int,
    val episodeNumber: Int
)