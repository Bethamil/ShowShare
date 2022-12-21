package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.MovieDbRestService
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException

@Controller
class HomePageController {

    @GetMapping("/")
    protected fun showPopulairSeries(): String {
        return "redirect:/allShows/popular/1"
    }

    @GetMapping("/allShows/{category}/{pageNumber}")
    protected fun showShows(
        @PathVariable pageNumber: Int,
        @PathVariable category: String,
        model: Model
    ): String {
        try {
            val allShows = MovieDbRestService().getShows(category, pageNumber)?.results
            if(allShows!!.isEmpty()) {
                throw ResponseStatusException(HttpStatus.NO_CONTENT)
            }
            model.addAttribute("shows", allShows)
            model.addAttribute("category", category)
            model.addAttribute("page", pageNumber)
            model.addAttribute("overviewPage", true)
            return "showTvShowsPage"
        } catch (e : Exception) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        }
    }
}