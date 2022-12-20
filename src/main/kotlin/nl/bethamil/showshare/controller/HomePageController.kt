package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.MovieDbRestService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

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
        val allShows = MovieDbRestService().getShows(category, pageNumber)?.results
        model.addAttribute("shows", allShows)
        model.addAttribute("category", category)
        model.addAttribute("page", pageNumber)
        return "showTvShowsPage"
    }
}