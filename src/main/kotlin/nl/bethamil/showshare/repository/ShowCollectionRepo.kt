package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.ShowCollection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.util.*


interface ShowCollectionRepo : JpaRepository<ShowCollection, Long> {

    @Query(
    "SELECT u FROM ShowCollection u WHERE u.showId=?1 AND u.user.id=?2")
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : Optional<ShowCollection>

    @Query(
        "SELECT u FROM ShowCollection u WHERE u.user.id=?1")
    fun findByUserId(user_id : Long) : List<ShowCollection>
}