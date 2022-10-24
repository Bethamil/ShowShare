package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.model.RatingShow
import nl.bethamil.showshare.repository.ShowRatingRepo
import nl.bethamil.showshare.service.ShowRatingService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShowRatingServiceImpl(private val showRatingRepo: ShowRatingRepo) : ShowRatingService {

    override fun save(ratingShow: RatingShow): RatingShow {
        return showRatingRepo.save(ratingShow)
    }

    override fun findByUserIdAndShowId(show_id: Long, user_id: Long): Optional<RatingShow> {
        return showRatingRepo.findByUserIdAndShowId(show_id, user_id)
    }
}