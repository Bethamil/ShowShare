package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.WatchlistVM
import org.springframework.data.domain.Pageable

interface WatchlistService {
    fun save(watchlistVM: WatchlistVM)
    fun deleteById(id : Long)
    fun findByUserId(id: Long, pageable: Pageable) : List<WatchlistVM>
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : WatchlistVM?
    fun countWatchlistByShowShareUserId(showShareUser_id: Long) : Int

}