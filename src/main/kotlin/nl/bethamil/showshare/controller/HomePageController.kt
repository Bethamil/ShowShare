package nl.bethamil.showshare.controller

import nl.bethamil.showshare.repository.ShowCollectionRepo
import nl.bethamil.showshare.repository.ShowShareUserRepo
import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import java.security.Principal
import javax.servlet.http.HttpServletRequest


@Controller
class HomePageController(val showCollectionRepo: ShowCollectionRepo, val showShareUserRepo: ShowShareUserRepo) {
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
    protected fun clickShow(@PathVariable showId: Int, model: Model, request : HttpServletRequest): String {
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(showId)
        model.addAttribute("show", selectedShow)
        val principal: Principal? = request.userPrincipal
        println(principal)


        if (principal != null) {
            val userId: Long? = showShareUserRepo.findByUsername(principal.name)?.get()?.id
            if (selectedShow?.id?.let { showCollectionRepo.findByUserIdAndShowId(it.toLong(), userId!!).isEmpty }
                == true) {
                model.addAttribute("notAdded", true)
            }
        }


        return "showDetails"
    }
}