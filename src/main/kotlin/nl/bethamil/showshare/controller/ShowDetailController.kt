package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.*
import nl.bethamil.showshare.repository.ShowCollectionRepo
import nl.bethamil.showshare.repository.ShowRatingRepo
import nl.bethamil.showshare.repository.ShowShareUserRepo
import nl.bethamil.showshare.service.RestService
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import java.security.Principal
import java.util.*
import javax.servlet.http.HttpServletRequest


@Controller
class ShowDetailController(
    val showCollectionRepo: ShowCollectionRepo,
    val showUserRepo: ShowShareUserRepo,
    val showShareUserRepo: ShowShareUserRepo,
    val showRatingRepo: ShowRatingRepo
) {

    @GetMapping("/show")
    protected fun showPopulairSeries(model: Model): String {
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(113988)
        model.addAttribute("show", selectedShow)
        return "showDetails"
    }

    @GetMapping("/show/{showId}")
    protected fun clickShow(@PathVariable showId: Int, model: Model, request: HttpServletRequest): String {
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(showId)
        if (selectedShow != null) {
            model.addAttribute("show", selectedShow)
        } else{
            model.addAttribute("show", Show())
        }
        val principal: Principal? = request.userPrincipal

        addModelAttributes(principal, selectedShow, model)

        return "showDetails"
    }

    private fun addModelAttributes(
        principal: Principal?,
        selectedShow: Show?,
        model: Model
    ) {
        if (principal != null) {
            val userId: Long? = showShareUserRepo.findByUsername(principal.name)?.get()?.id
            if (selectedShow?.id?.let { showCollectionRepo.findByUserIdAndShowId(it.toLong(), userId!!).isEmpty }
                == true) {
                model.addAttribute("notAdded", true)
            }
            if (selectedShow?.id?.toLong()?.let { showRatingRepo.findByUserIdAndShowId(it, userId!!).isPresent }
                == true) {
                val selectedRating: RatingShow =
                    showRatingRepo.findByUserIdAndShowId(selectedShow.id.toLong(), userId!!).get()
                model.addAttribute("ratingFromDB", selectedRating)
            } else {
                val ratingShow = RatingShow(ratingId = -1)
                model.addAttribute("ratingFromDB", ratingShow)
            }
        }
    }

    @PostMapping("/show/add")
    protected fun addToCollection(
        @ModelAttribute("saveShowId") showId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser? = showUserRepo.findByUsername(principal.name)!!.orElse(ShowShareUser())
        val showCollection = ShowCollection(showId = showId, user = currentUser!!)
        if (!result.hasErrors()) {
            showCollectionRepo.save(showCollection)
        }
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/delete")
    protected fun deleteFromCollection(
        @ModelAttribute("saveShowId") showId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser? = showUserRepo.findByUsername(principal.name)!!.orElse(ShowShareUser())
        val showCollection = showCollectionRepo.findByUserIdAndShowId(show_id = showId,
            user_id = currentUser!!.id!!.toLong()
        )
        if (!result.hasErrors()) {
            println(showCollection.toString() + "DELETED")
            showCollectionRepo.delete(showCollection.get())
        }
        return "redirect:/show/$showId"
    }

    @PostMapping("/show/addRating")
    protected fun addRating(
        @ModelAttribute("saveShowId") showId: Long,
        @ModelAttribute("yourRating") rating: Double,
        @ModelAttribute("ratingDB") ratingId: Long,
        result: BindingResult,
        request: HttpServletRequest
    ): String {

        val principal: Principal = request.userPrincipal
        val currentUser: ShowShareUser? = showUserRepo.findByUsername(principal.name)!!.orElse(ShowShareUser())
        val ratingShow = if (ratingId != -1L) {
            RatingShow(ratingId = ratingId, user = currentUser!!, rating = rating, showId = showId)
        } else {
            RatingShow(user=currentUser!!, rating = rating, showId = showId)
        }
        showRatingRepo.save(ratingShow)
        return "redirect:/show/$showId"
    }

    @GetMapping("/show/{showId}/{seasonNumber}")
    protected fun seasonDetails(
        @PathVariable showId: Int,
        @PathVariable seasonNumber: Int,
        model: Model,
        request: HttpServletRequest
    ): String {

        val seasonData = RestService(RestTemplateBuilder()).getSeasonInfo(showId, seasonNumber)
        val selectedShow = RestService(RestTemplateBuilder()).getSingleShow(showId)

        if (seasonData != null) {
            model.addAttribute("season", seasonData)
        } else {
            model.addAttribute("season", SeasonData())
        }
        model.addAttribute("show", selectedShow)
        return "seasonDetails"
    }


}