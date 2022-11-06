package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.WatchedEpisodeVM


interface WatchedEpisodeService {
    fun save(watchedEpisodeVM: WatchedEpisodeVM)
    fun delete(watchedEpisodeVM: WatchedEpisodeVM)
    fun isInDb(watchedEpisodeVM: WatchedEpisodeVM) : Boolean
    fun findWatchedEpisode(watchedEpisodeVM: WatchedEpisodeVM) : WatchedEpisodeVM?

}