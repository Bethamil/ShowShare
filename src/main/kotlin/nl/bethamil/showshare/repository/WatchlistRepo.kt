package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.Watchlist
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface WatchlistRepo : JpaRepository<Watchlist, Long> {
    fun findByShowShareUserId(showShareUserId : Long) : List<Watchlist>
    fun findByShowShareUserIdAndShowId(showShareUserId: Long, showId: Long) : Optional<Watchlist>
}