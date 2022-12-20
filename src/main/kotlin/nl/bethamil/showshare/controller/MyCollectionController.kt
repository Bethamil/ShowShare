package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.Show
import nl.bethamil.showshare.service.MovieDbRestService
import nl.bethamil.showshare.service.MyFavoritesService
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.service.WatchlistService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import javax.servlet.http.HttpServletRequest

@Controller
class MyCollectionController(
    val myFavoritesService: MyFavoritesService,
    val showShareUserService: ShowShareUserService,
    val watchlistService: WatchlistService
) {

    @GetMapping("/myFavorites")
    protected fun myFavorites(model: Model, request: HttpServletRequest): String {
        val userId = showShareUserService.findByUsername(request.userPrincipal.name)!!.id
        val showList = myFavoritesService.findByUserId(userId!!.toLong())
        val listOfUserShows: List<Show> = showList.map {
            MovieDbRestService().getSingleShow(it.showId.toInt())!!
        }

        val favorite = "favorites"
        model.addAttribute("category", favorite)
        model.addAttribute("shows", listOfUserShows)
        return "showTvShowsPage"
    }

    @GetMapping("/watchlist")
    protected fun watchlist(model: Model, request: HttpServletRequest): String {
        val userId = showShareUserService.findByUsername(request.userPrincipal.name)!!.id!!
        val showList = watchlistService.findByUserId(userId)
        val listOfUserShows: List<Show> =
            showList.map { MovieDbRestService().getSingleShow(it.showId.toInt())!! }
        model.addAttribute("category", "watchlist")
        model.addAttribute("shows", listOfUserShows)
        return "showTvShowsPage"
    }
}