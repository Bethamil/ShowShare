package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.repository.ShowWatchedRepo
import nl.bethamil.showshare.service.WatchedEpisodeService
import nl.bethamil.showshare.viewmodel.ModelViewMapper
import nl.bethamil.showshare.viewmodel.WatchedEpisodeVM
import org.springframework.stereotype.Service

@Service
class WatchedEpisodeImpl(val watchedRepo: ShowWatchedRepo) : WatchedEpisodeService, ModelViewMapper {
    override fun save(watchedEpisodeVM: WatchedEpisodeVM) {
        watchedRepo.save(watchedEpisodeVM.toModel())
    }

    override fun delete(watchedEpisodeVM: WatchedEpisodeVM) {
        watchedRepo.delete(watchedEpisodeVM.toModel())
    }

    override fun isInDb(watchedEpisodeVM: WatchedEpisodeVM): Boolean {
        val watch = watchedRepo.isInDb(
            watchedEpisodeVM.showId,
            watchedEpisodeVM.seasonNumber,
            watchedEpisodeVM.episodeNumber,
            watchedEpisodeVM.showShareUser.id!!
        )
        if (watch != null) {
            return false
        }
        return true
    }

    override fun findWatchedEpisode(watchedEpisodeVM: WatchedEpisodeVM): WatchedEpisodeVM? {
        val watch = watchedRepo.isInDb(
            watchedEpisodeVM.showId,
            watchedEpisodeVM.seasonNumber,
            watchedEpisodeVM.episodeNumber,
            watchedEpisodeVM.showShareUser.id!!
        )
        if (watch != null) {
            return watch.toVm()
        }
        return null
    }
}