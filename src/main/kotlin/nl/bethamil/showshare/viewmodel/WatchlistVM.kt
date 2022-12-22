package nl.bethamil.showshare.viewmodel

import java.time.LocalDateTime

data class WatchlistVM(
    var watchlistId: Long? = null,
    val showId: Long,
    val showShareUserVM: ShowShareUserVM,
    val localDateTime: LocalDateTime
)