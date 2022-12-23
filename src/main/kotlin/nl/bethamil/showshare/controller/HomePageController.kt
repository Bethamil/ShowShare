package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.MovieDbRestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.server.ResponseStatusException
import java.util.Random

@Controller
class HomePageController {

    @GetMapping("/")
    protected fun homePage(model: Model): String {
        val randomPagePopular = Random().nextInt(1,2)
        val latestShow = MovieDbRestService(RestTemplateBuilder())
            .getShows("popular", randomPagePopular)?.results?.shuffled()!!.take(3)
        val topShows = MovieDbRestService(RestTemplateBuilder())
            .getShows("top_rated", Random().nextInt(1,3))?.results
        val randomValues = List(3) { Random().nextInt(0, 20) }

        model.addAttribute("top_show1", topShows?.get(randomValues[0])?.backdrop_path)
        model.addAttribute("top_show2", topShows?.get(randomValues[1])?.backdrop_path)
        model.addAttribute("top_show3", topShows?.get(randomValues[2])?.backdrop_path)
        model.addAttribute("pop_shows", latestShow)
        model.addAttribute("randomNumber", randomValues)
        return "index"
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