package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.MovieDbRestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class SearchController {
    @RequestMapping("/search")
    fun searchedShow(@ModelAttribute("query") query: String , model : Model) : String {
        if (query.isNotEmpty()) {
            val searchedShow = MovieDbRestService(RestTemplateBuilder()).getShowByQuery(query)?.results
            model.addAttribute("shows", searchedShow)
            return "searchedShowPage"
        }
        return "redirect:/"
    }



}