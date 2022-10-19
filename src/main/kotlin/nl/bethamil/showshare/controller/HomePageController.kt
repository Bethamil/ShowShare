package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@Controller
class HomePageController {
    @GetMapping("/", "/populair")
    protected fun showPopulairSeries(model: Model): String {
        val populairShows = RestService(RestTemplateBuilder()).getPopulairSeries(1)?.results
        model.addAttribute("shows", populairShows)
        return "mostPopulair"
    }

    @GetMapping("/populair/{pageNumber}")
    protected fun showPopulairSeries(@PathVariable pageNumber: Int, model: Model): String {
        val populairShows = RestService(RestTemplateBuilder()).getPopulairSeries(pageNumber)?.results
        model.addAttribute("shows", populairShows)
        return "mostPopulair"
    }

    @GetMapping("/show/{showId}")
    protected fun clickShow(@PathVariable showId: Int, model: Model): String {
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(showId)
        model.addAttribute("show", selectedShow)
        return "showDetails"
    }
}