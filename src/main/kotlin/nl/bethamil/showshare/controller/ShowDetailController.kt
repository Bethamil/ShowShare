package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.*
import nl.bethamil.showshare.service.*
import nl.bethamil.showshare.viewmodel.MyFavoritesVM
import nl.bethamil.showshare.viewmodel.RatingShowVM
import nl.bethamil.showshare.viewmodel.ShowShareUserVM
import nl.bethamil.showshare.viewmodel.WatchlistVM
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.server.ResponseStatusException
import java.util.*
import javax.servlet.http.HttpServletRequest


@Controller
class ShowDetailController(
    val myFavoritesService: MyFavoritesService,
    val showShareUserService: ShowShareUserService,
    val showRatingService: ShowRatingService,
    val watchlistService: WatchlistService
) {

    @GetMapping("/show/{showId}")
    protected fun clickShow(@PathVariable showId: Int, model: Model, request: HttpServletRequest): String {
        val selectedShow = MovieDbRestService(RestTemplateBuilder()).getSingleShow(showId)
        val trailer = MovieDbRestService().getShowTrailer(showId)?.results?.
        filter { it.official && it.type=="Trailer" && it.site=="YouTube" }

        if (trailer!!.isNotEmpty()) {
            model.addAttribute("trailerData", trailer[0])
            model.addAttribute("TrailerAvailable", true)
        }

        if (selectedShow != null) {
            model.addAttribute("show", selectedShow)
        } else {
            model.addAttribute("show", Show())
        }
        addModelAttributes(request, selectedShow, model)
        return "showDetails"
    }

    private fun addModelAttributes(
        request: HttpServletRequest,
        selectedShow: Show?,
        model: Model
    ) {
        if (request.userPrincipal != null) {
            val userId: Long? = getLoggedInUser(request).id
            buttonChecks(selectedShow, userId, model)
            if (selectedShow?.id?.toLong()?.let { showRatingService.findByUserIdAndShowId(it, userId!!) != null }
                == true) {
                val selectedRating =
                    showRatingService.findByUserIdAndShowId(selectedShow.id.toLong(), userId!!)
                model.addAttribute("ratingFromDB", selectedRating)
            } else {
                val ratingShow = RatingShowVM(ratingId = -1)
                model.addAttribute("ratingFromDB", ratingShow)
            }
        }
    }

    private fun buttonChecks(
        selectedShow: Show?,
        userId: Long?,
        model: Model
    ) {
        if (selectedShow?.id?.let { myFavoritesService.findByUserIdAndShowId(it.toLong(), userId!!) == null }
            == true) {
            model.addAttribute("notAddedToFavorites", true)
        }
        if (selectedShow?.id?.let {
                watchlistService.findByUserIdAndShowId(
                    it.toLong(),
                    userId!!
                ) == null
            } == true) {
            model.addAttribute("notAddedToWatchlist", true)
        }
    }

    @PostMapping("/show/addToFavorites")
    protected fun addToCollection(
        @ModelAttribute("saveShowId") showId: Long, request: HttpServletRequest
    ): String {
        val currentUser = getLoggedInUser(request)
        val myFavorites = MyFavoritesVM(showId = showId, user = currentUser)
        myFavoritesService.save(myFavorites)
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/addToWatchList")
    protected fun addToWatchlist(
        @ModelAttribute("saveShowId") showId: Long, request: HttpServletRequest
    ): String {
        val currentUser = getLoggedInUser(request)
        val watchlistVM = WatchlistVM(showId = showId, showShareUserVM = currentUser)
        watchlistService.save(watchlistVM)
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/deleteFromFavorites")
    protected fun deleteFromCollection(
        @ModelAttribute("saveShowId") showId: Long, request: HttpServletRequest
    ): String {

        val currentUser = getLoggedInUser(request)
        val showCollection = myFavoritesService.findByUserIdAndShowId(
            show_id = showId,
            user_id = currentUser.id!!.toLong()
        )
        myFavoritesService.deleteById(showCollection?.id!!)
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/deleteFromWatchlist")
    protected fun deleteFromWatchlist(@ModelAttribute("saveShowId") showId: Long, request: HttpServletRequest)
            : String {
        val currentUser = getLoggedInUser(request)
        val watchlistVM = watchlistService.findByUserIdAndShowId(user_id = currentUser.id!!, show_id = showId)
        watchlistService.deleteById(watchlistVM!!.watchlistId!!)
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/addRating")
    protected fun addRating(
        @ModelAttribute("saveShowId") showId: Long, @ModelAttribute("yourRating") rating: Double,
        @ModelAttribute("ratingDB") ratingId: Long, request: HttpServletRequest
    ): String {
        if (rating > 10 || rating < 1) {
            throw ResponseStatusException(HttpStatus.NOT_ACCEPTABLE)
        }
        val currentUser = getLoggedInUser(request)
        val ratingShow = if (ratingId != -1L) {
            RatingShowVM(ratingId = ratingId, user = currentUser, rating = rating, showId = showId)
        } else {
            RatingShowVM(user = currentUser, rating = rating, showId = showId)
        }
        showRatingService.save(ratingShow)
        return "redirect:/show/$showId"
    }

    private fun getLoggedInUser(request: HttpServletRequest): ShowShareUserVM {
        return showShareUserService.findByUsername(request.userPrincipal.name)!!
    }
}