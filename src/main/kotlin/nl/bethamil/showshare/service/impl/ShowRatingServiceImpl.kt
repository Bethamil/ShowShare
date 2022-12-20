package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.mapper.ModelViewMapper
import nl.bethamil.showshare.repository.ShowRatingRepo
import nl.bethamil.showshare.service.ShowRatingService
import nl.bethamil.showshare.viewmodel.RatingShowVM
import org.springframework.stereotype.Service

@Service
class ShowRatingServiceImpl(private val showRatingRepo: ShowRatingRepo) : ShowRatingService, ModelViewMapper {

    override fun save(ratingShowVM: RatingShowVM) {
        showRatingRepo.save(ratingShowVM.toModel())
    }

    override fun findByUserIdAndShowId(show_id: Long, user_id: Long): RatingShowVM? {
        val rating = showRatingRepo.findByUserIdAndShowId(show_id, user_id)
        if(rating.isPresent) {
            return rating.get().toVM()
        }
        return null
    }
}