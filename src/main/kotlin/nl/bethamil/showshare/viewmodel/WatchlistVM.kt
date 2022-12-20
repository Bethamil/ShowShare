package nl.bethamil.showshare.viewmodel

data class WatchlistVM(
    var watchlistId: Long? = null,
    val showId: Long,
    val showShareUserVM: ShowShareUserVM
)