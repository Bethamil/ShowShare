package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.RatingShowVM

interface ShowRatingService {
    fun save(ratingShowVM: RatingShowVM)
    fun findByUserIdAndShowId(show_id: Long, user_id: Long): RatingShowVM?

}