package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.Show
import nl.bethamil.showshare.repository.ShowCollectionRepo
import nl.bethamil.showshare.repository.ShowShareUserRepo
import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@Controller
class MyCollectionController(val showCollectionRepo: ShowCollectionRepo, val showShareUserRepo: ShowShareUserRepo) {

    @GetMapping("/myCollection")
    protected fun myCollection(model: Model, request : HttpServletRequest) : String {
        val principal: Principal = request.userPrincipal
        val userId = showShareUserRepo.findByUsername(principal.name)?.get()?.id
        val showList = showCollectionRepo.findByUserId(userId!!.toLong())
        val listOfUserShows = ArrayList<Show>()
        showList.forEach { s ->
            RestService(RestTemplateBuilder()).getSingleShow(s.showId.toInt())
                ?.let { listOfUserShows.add(it) }
        }

        val favorite = "favorites"

        model.addAttribute("category", favorite)
        model.addAttribute("shows", listOfUserShows)
        return "topShowsPage"

    }
}