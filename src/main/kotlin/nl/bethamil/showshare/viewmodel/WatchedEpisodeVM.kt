package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.ShowShareUser

class WatchedEpisodeVM(
    var viewDateId: Long? = null,
    val showShareUser: ShowShareUser,
    val showId : Int,
    val seasonNumber : Int,
    val episodeNumber : Int
)
 {
}