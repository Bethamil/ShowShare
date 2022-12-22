package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.MyFavorites
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

import java.util.*


interface MyFavoritesRepo : JpaRepository<MyFavorites, Long> {

    @Query(
        "SELECT u FROM MyFavorites u WHERE u.showId=?1 AND u.user.id=?2"
    )
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : Optional<MyFavorites>

    @Query(
        "SELECT u FROM MyFavorites u WHERE u.user.id=?1 ORDER BY u.id desc "
    )
    fun findByUserId(user_id : Long, pageable: Pageable) : List<MyFavorites>

    fun countMyFavoritesByUserId(user_id: Long) : Long
}