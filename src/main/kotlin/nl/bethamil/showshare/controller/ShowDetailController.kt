package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.*
import nl.bethamil.showshare.service.*
import nl.bethamil.showshare.viewmodel.ModelViewMapper
import nl.bethamil.showshare.viewmodel.ShowVM
import nl.bethamil.showshare.viewmodel.WatchedEpisodeVM
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest


@Controller
class ShowDetailController(
    val showCollectionRepo: ShowCollectionService,
    val showShareUserService: ShowShareUserService,
    val showRatingService: ShowRatingService,
    val watchedEpisodeService: WatchedEpisodeService,
) : ModelViewMapper {

    @GetMapping("/show")
    protected fun showPopulairSeries(model: Model): String {
        val selectedShow = MovieDbRestService(RestTemplateBuilder()).getSingleShow(113988)
        model.addAttribute("show", selectedShow)
        return "showDetails"
    }

    @GetMapping("/show/{showId}")
    protected fun clickShow(@PathVariable showId: Int, model: Model, request: HttpServletRequest): String {
        val selectedShow = MovieDbRestService(RestTemplateBuilder()).getSingleShow(showId)?.toShowVM()

        if (selectedShow != null) {
            model.addAttribute("show", selectedShow)
        } else {
            model.addAttribute("show", Show().toShowVM())
        }
        val principal: Principal? = request.userPrincipal

        addModelAttributes(principal, selectedShow, model)

        return "showDetails"
    }

    private fun addModelAttributes(
        principal: Principal?,
        selectedShow: ShowVM?,
        model: Model
    ) {
        if (principal != null) {
            val userId: Long? = showShareUserService.findByUsername(principal.name).get().id
            if (selectedShow?.id?.let { showCollectionRepo.findByUserIdAndShowId(it.toLong(), userId!!).isEmpty }
                == true) {
                model.addAttribute("notAdded", true)
            }
            if (selectedShow?.id?.toLong()?.let { showRatingService.findByUserIdAndShowId(it, userId!!).isPresent }
                == true) {
                val selectedRating: RatingShow =
                    showRatingService.findByUserIdAndShowId(selectedShow.id!!.toLong(), userId!!).get()
                model.addAttribute("ratingFromDB", selectedRating.toRatinVM())
            } else {
                val ratingShow = RatingShow(ratingId = -1)
                model.addAttribute("ratingFromDB", ratingShow.toRatinVM())
            }
        }
    }

    @PostMapping("/show/add")
    protected fun addToCollection(
        @ModelAttribute("saveShowId") showId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser = showShareUserService.findByUsername(principal.name).get()
        val showCollection = ShowCollection(showId = showId, user = currentUser)
        if (!result.hasErrors()) {
            showCollectionRepo.save(showCollection)
        }
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/delete")
    protected fun deleteFromCollection(
        @ModelAttribute("saveShowId") showId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser = showShareUserService.findByUsername(principal.name).get()
        val showCollection = showCollectionRepo.findByUserIdAndShowId(
            show_id = showId,
            user_id = currentUser.id!!.toLong()
        )
        if (!result.hasErrors()) {
            println(showCollection.toString() + "DELETED")
            showCollectionRepo.delete(showCollection.get())
        }
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/addRating")
    protected fun addRating(
        @ModelAttribute("saveShowId") showId: Long,
        @ModelAttribute("yourRating") rating: Double,
        @ModelAttribute("ratingDB") ratingId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser = showShareUserService.findByUsername(principal.name).get()
        val ratingShow = if (ratingId != -1L) {
            RatingShow(ratingId = ratingId, user = currentUser, rating = rating, showId = showId)
        } else {
            RatingShow(user = currentUser, rating = rating, showId = showId)
        }
        showRatingService.save(ratingShow)
        return "redirect:/show/$showId"
    }

    @GetMapping("/show/{showId}/{seasonNumber}")
    protected fun seasonDetails(
        @PathVariable showId: Int,
        @PathVariable seasonNumber: Int,
        model: Model,
        request: HttpServletRequest
    ): String {

        val seasonData = MovieDbRestService(RestTemplateBuilder()).getSeasonInfo(showId, seasonNumber)?.toSeasonDataVM()
        val selectedShow = MovieDbRestService(RestTemplateBuilder()).getSingleShow(showId)?.toShowVM()

//        val ifInDbList = seasonData?.episodes?.map { episode -> watchedEpisodeService.isInDb(
//            watchedEpisodeVM = WatchedEpisodeVM(showId = episode.show_id!!,
//                seasonNumber = episode.season_number!!,
//                episodeNumber = episode.episode_number!!, showShareUser = getLoggedInUser(request))) }
//        println(ifInDbList)
//        model.addAttribute("ifInDbList", ifInDbList)

        if (seasonData != null) {
            model.addAttribute("season", seasonData)
        } else {
            model.addAttribute("season", SeasonData().toSeasonDataVM())
        }
        model.addAttribute("show", selectedShow)
        return "seasonDetails"
    }

    @PostMapping("/show/addWatched/{showId}/{seasonNumber}/{episodeNumber}")
    protected fun saveHaveIWatchedThis(
        @PathVariable seasonNumber: Int, @PathVariable showId: Int, @PathVariable episodeNumber: Int,
        request: HttpServletRequest

    ): String {
        val watchedEpisodeVM = WatchedEpisodeVM(
            showShareUser = getLoggedInUser(request),
            showId = showId, seasonNumber = seasonNumber, episodeNumber = episodeNumber
        )
        watchedEpisodeService.save(watchedEpisodeVM)
        println("$seasonNumber/$showId/$episodeNumber")
        return "redirect:/"
    }

    protected fun getLoggedInUser(request: HttpServletRequest): ShowShareUser {
        return showShareUserService.findByUsername(request.userPrincipal.name).get()
    }
}