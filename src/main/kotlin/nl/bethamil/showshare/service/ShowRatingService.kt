package nl.bethamil.showshare.service

import nl.bethamil.showshare.model.RatingShow
import java.util.*

interface ShowRatingService {
    fun save(ratingShow: RatingShow) : RatingShow
    fun findByUserIdAndShowId(show_id: Long, user_id: Long): Optional<RatingShow>

}