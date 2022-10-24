package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.ShowShareUser

data class ShowCollectionVM(
    var id: Long? = null,
    val user: ShowShareUser,
    val showId: Long
)