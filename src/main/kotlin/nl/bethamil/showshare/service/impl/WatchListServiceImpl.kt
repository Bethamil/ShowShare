package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.repository.WatchlistRepo
import nl.bethamil.showshare.service.WatchlistService
import nl.bethamil.showshare.mapper.ModelViewMapper
import nl.bethamil.showshare.viewmodel.WatchlistVM
import org.springframework.stereotype.Service

@Service
class WatchListServiceImpl(private val watchlistRepo : WatchlistRepo) : WatchlistService, ModelViewMapper {


    override fun save(watchlistVM: WatchlistVM) {
        watchlistRepo.save(watchlistVM.toModel())
    }

    override fun deleteById(id: Long) {
        watchlistRepo.deleteById(id)
    }

    override fun findByUserId(id: Long) : List<WatchlistVM> {
        return watchlistRepo.findByShowShareUserId(id).map { Watchlist -> Watchlist.toVM() }
    }

    override fun findByUserIdAndShowId(show_id: Long, user_id: Long): WatchlistVM? {
        val watchlist = watchlistRepo.findByShowShareUserIdAndShowId(user_id, show_id)
        return if (watchlist.isPresent) {
            watchlist.get().toVM()
        } else null
    }
}
