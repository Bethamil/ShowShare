package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.WatchedEpisode
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ShowWatchedRepo : JpaRepository<WatchedEpisode, Long> {

    @Query("SELECT u FROM WatchedEpisode u WHERE u.showId=?1 AND u.seasonNumber=?2 AND u.episodeNumber=?3 " +
            "AND u.showShareUser.id=?4")
    fun isInDb(show_id : Int, season_number : Int, episode_number : Int, show_share_user_id : Long): WatchedEpisode?
}