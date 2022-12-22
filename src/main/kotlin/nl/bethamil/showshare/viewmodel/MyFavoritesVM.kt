package nl.bethamil.showshare.viewmodel

import java.time.LocalDateTime


data class MyFavoritesVM(
    var id: Long? = null,
    val user: ShowShareUserVM,
    val showId: Long,
    val localDateTime: LocalDateTime

)