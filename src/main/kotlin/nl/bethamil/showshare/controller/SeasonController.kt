package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.SeasonData
import nl.bethamil.showshare.model.ShowShareUser
import nl.bethamil.showshare.service.*
import nl.bethamil.showshare.viewmodel.ModelViewMapper
import nl.bethamil.showshare.viewmodel.WatchedEpisodeVM
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest

@Controller
class SeasonController(
    val showShareUserService: ShowShareUserService,

    val watchedEpisodeService: WatchedEpisodeService,
) : ModelViewMapper {

    @GetMapping("/show/{showId}/{seasonNumber}")
    protected fun seasonDetails(
        @PathVariable showId: Int, @PathVariable seasonNumber: Int,
        model: Model,
        request: HttpServletRequest
    ): String {
        val seasonData = MovieDbRestService(RestTemplateBuilder()).getSeasonInfo(showId, seasonNumber)?.toSeasonDataVM()
        if (seasonData != null && request.userPrincipal != null) {
            seasonData.episodes?.forEach { e ->
                e.notWatched = watchedEpisodeService.isInDb(
                    WatchedEpisodeVM(
                        showShareUser = getLoggedInUser(request), showId = showId, seasonNumber = seasonNumber,
                        episodeNumber = e.episode_number!!
                    )
                )
            }
        }
        val selectedShow = MovieDbRestService(RestTemplateBuilder()).getSingleShow(showId)?.toShowVM()

        if (seasonData != null) {
            model.addAttribute("season", seasonData)
        } else {
            model.addAttribute("season", SeasonData().toSeasonDataVM())
        }
        model.addAttribute("show", selectedShow)
        return "seasonDetails"
    }

    @PostMapping("/show/addWatched/{showId}/{seasonNumber}/{episodeNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    protected fun saveHaveIWatchedThis(
        @PathVariable seasonNumber: Int, @PathVariable showId: Int, @PathVariable episodeNumber: Int,
        request: HttpServletRequest) {
        val watchedEpisodeVM = WatchedEpisodeVM(
            showShareUser = getLoggedInUser(request),
            showId = showId, seasonNumber = seasonNumber, episodeNumber = episodeNumber
        )
        if (watchedEpisodeService.findWatchedEpisode(watchedEpisodeVM) == null)
            watchedEpisodeService.save(watchedEpisodeVM)
    }

    @PostMapping("/show/deleteWatched/{showId}/{seasonNumber}/{episodeNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    protected fun deleteWatched(
        @PathVariable seasonNumber: Int, @PathVariable showId: Int, @PathVariable episodeNumber: Int,
        request: HttpServletRequest) {
        val watchedEpisodeVM = WatchedEpisodeVM(
            showShareUser = getLoggedInUser(request),
            showId = showId, seasonNumber = seasonNumber, episodeNumber = episodeNumber
        )
        watchedEpisodeService.findWatchedEpisode(watchedEpisodeVM)?.let { watchedEpisodeService.delete(it) }
    }

    @PostMapping("/show/watchedSeason/{showId}/{seasonNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    protected fun saveWatchedSeason(
        @PathVariable seasonNumber: Int, @PathVariable showId: Int, request: HttpServletRequest,
    ) {
        val selectedSeason =
            MovieDbRestService(RestTemplateBuilder()).getSeasonInfo(showId, seasonNumber)!!.toSeasonDataVM()
        for (episode in selectedSeason.episodes!!) {
            val watchedEpisodeVM = WatchedEpisodeVM(
                showShareUser = getLoggedInUser(request),
                showId = showId, seasonNumber = seasonNumber, episodeNumber = episode.episode_number!!
            )
            if (watchedEpisodeService.isInDb(watchedEpisodeVM)) {
                watchedEpisodeService.save(watchedEpisodeVM)
            }
        }
    }

    @PostMapping("/show/unwatchSeason/{showId}/{seasonNumber}")
    @ResponseStatus(value = HttpStatus.OK)
    protected fun unwatchSeason(
        @PathVariable seasonNumber: Int, @PathVariable showId: Int,
        request: HttpServletRequest) {
        val selectedSeason =
            MovieDbRestService(RestTemplateBuilder()).getSeasonInfo(showId, seasonNumber)!!.toSeasonDataVM()
        for (episode in selectedSeason.episodes!!) {
            val watchedEpisodeVM = WatchedEpisodeVM(
                showShareUser = getLoggedInUser(request),
                showId = showId, seasonNumber = seasonNumber, episodeNumber = episode.episode_number!!
            )
            if (!watchedEpisodeService.isInDb(watchedEpisodeVM)) {
                watchedEpisodeService.findWatchedEpisode(watchedEpisodeVM)?.let { watchedEpisodeService.delete(it) }
            }
        }
    }



    protected fun getLoggedInUser(request: HttpServletRequest): ShowShareUser {
        return showShareUserService.findByUsername(request.userPrincipal.name).get()
    }
}