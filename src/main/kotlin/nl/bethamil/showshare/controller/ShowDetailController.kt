package nl.bethamil.showshare.controller

import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ShowDetailController {

    @GetMapping("/show")
    protected fun showPopulairSeries(model: Model): String {
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(113988)
        model.addAttribute("show", selectedShow)
        return "showDetails"
    }
}