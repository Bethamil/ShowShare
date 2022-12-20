package nl.bethamil.showshare.viewmodel


data class MyFavoritesVM(
    var id: Long? = null,
    val user: ShowShareUserVM,
    val showId: Long
)