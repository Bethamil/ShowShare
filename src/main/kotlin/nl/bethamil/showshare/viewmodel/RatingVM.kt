package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.ShowShareUser

data class RatingVM(val ratingId: Long? = null, val user: ShowShareUser? = null, val rating: Double? = null, val showId: Long? = null)
{
}