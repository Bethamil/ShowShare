package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.MovieDbRestService
import nl.bethamil.showshare.service.ShowCollectionService
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.viewmodel.ModelViewMapper
import nl.bethamil.showshare.viewmodel.ShowVM
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import java.security.Principal
import javax.servlet.http.HttpServletRequest

@Controller
class MyCollectionController(val showCollectionService: ShowCollectionService,
                             val showShareUserService: ShowShareUserService) : ModelViewMapper{

    @GetMapping("/myFavorites")
    protected fun myCollection(model: Model, request : HttpServletRequest) : String {
        val principal: Principal = request.userPrincipal
        val userId = showShareUserService.findByUsername(principal.name).get().id
        val showList = showCollectionService.findByUserId(userId!!.toLong())
        val listOfUserShows : List<ShowVM> = showList.map{MovieDbRestService().getSingleShow(it.showId.toInt())!!
            .toShowVM()}

        val favorite = "favorites"
        model.addAttribute("category", favorite)
        model.addAttribute("shows", listOfUserShows)
        return "topShowsPage"
    }
}