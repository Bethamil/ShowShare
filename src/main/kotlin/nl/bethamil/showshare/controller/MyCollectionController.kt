package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.Show
import nl.bethamil.showshare.service.MovieDbRestService
import nl.bethamil.showshare.service.MyFavoritesService
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.service.WatchlistService
import nl.bethamil.showshare.viewmodel.ShowShareUserVM
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.server.ResponseStatusException
import javax.servlet.http.HttpServletRequest

private const val AMOUNTOFSHOWSONPAGE = 20

@Controller
class MyCollectionController(
    val myFavoritesService: MyFavoritesService,
    val showShareUserService: ShowShareUserService,
    val watchlistService: WatchlistService
) {

    @GetMapping("/myFavorites")
    protected fun myFavorites(@RequestParam(defaultValue = "1") page : Int, model: Model, request: HttpServletRequest): String {
        val user = showShareUserService.findByUsername(request.userPrincipal.name)
        return addToFavorites(user, page, model)
    }

    @GetMapping("/favorites/{userId}")
    protected fun favorites(@RequestParam(defaultValue = "1") page : Int, model: Model, request: HttpServletRequest,
                            @PathVariable userId: Long): String {
        val user = showShareUserService.findById(userId)
        return addToFavorites(user, page, model)
    }

    private fun addToFavorites(
        user: ShowShareUserVM?,
        page: Int,
        model: Model
    ): String {
        val amountOfFavorites = myFavoritesService.countMyFavoritesByUserId(user!!.id!!)
        val pageable: Pageable = PageRequest.of(page - 1, AMOUNTOFSHOWSONPAGE,
            Sort.by("localDateTime").descending())
        val showList = myFavoritesService.findByUserId(user.id!!.toLong(), pageable)
        val listOfUserShows: List<Show> = showList.map {
            MovieDbRestService().getSingleShow(it.showId.toInt())!!
        }
        if ((page-1)* AMOUNTOFSHOWSONPAGE > amountOfFavorites) {
            throw  ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        model.addAttribute("page", page)
        model.addAttribute("maxAmount", amountOfFavorites)
        model.addAttribute("user", user)
        model.addAttribute("category", "favorites")
        model.addAttribute("shows", listOfUserShows)
        return "showTvShowsPage"
    }

    @GetMapping("/watchlist")
    protected fun watchlist(@RequestParam(defaultValue = "1") page: Int, model: Model, request: HttpServletRequest): String {
        val userId = showShareUserService.findByUsername(request.userPrincipal.name)!!.id!!
        val maxAmount = watchlistService.countWatchlistByShowShareUserId(userId)

        if ((page-1)* AMOUNTOFSHOWSONPAGE > maxAmount) {
            throw  ResponseStatusException(HttpStatus.NOT_FOUND)
        }

        val pageable: Pageable = PageRequest.of(page - 1, AMOUNTOFSHOWSONPAGE,
            Sort.by("localDateTime").descending())
        val showList = watchlistService.findByUserId(userId, pageable)
        val listOfUserShows: List<Show> =
            showList.map { MovieDbRestService().getSingleShow(it.showId.toInt())!! }

        model.addAttribute("page", page)
        model.addAttribute("maxAmount", maxAmount)
        model.addAttribute("category", "watchlist")
        model.addAttribute("shows", listOfUserShows)
        return "showTvShowsPage"
    }
}