package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.WatchlistVM

interface WatchlistService {
    fun save(watchlistVM: WatchlistVM)
    fun deleteById(id : Long)
    fun findByUserId(id : Long) : List<WatchlistVM>
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : WatchlistVM?
}