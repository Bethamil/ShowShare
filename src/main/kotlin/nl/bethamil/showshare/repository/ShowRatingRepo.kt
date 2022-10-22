package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.RatingShow
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*

interface ShowRatingRepo : JpaRepository<RatingShow, Long> {
    @Query(
        "SELECT u FROM RatingShow u WHERE u.showId=?1 AND u.user.id=?2"
    )
    fun findByUserIdAndShowId(show_id: Long, user_id: Long): Optional<RatingShow>
}