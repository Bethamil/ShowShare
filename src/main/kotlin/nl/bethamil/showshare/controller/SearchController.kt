package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class SearchController {

//    @GetMapping("/search/{query}")
//    fun searchedShow(@PathVariable query : String, model : Model) : String {
//        val searchedShow = RestService(RestTemplateBuilder()).getShowByQuery(query)?.results
//        model.addAttribute("shows",searchedShow)
//        return "searchedShowPage"
//    }

    @RequestMapping("/search")
    fun searchedShow(@ModelAttribute("query") query: String , model : Model) : String {
        if (query.isNotEmpty()) {
            val searchedShow = RestService(RestTemplateBuilder()).getShowByQuery(query)?.results
            model.addAttribute("shows", searchedShow)
            return "searchedShowPage"
        }
        return "redirect:/"
    }



}