package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.Show
import nl.bethamil.showshare.reCaptcha.Captcha
import nl.bethamil.showshare.service.CaptchaService
import nl.bethamil.showshare.service.MovieDbRestService
import nl.bethamil.showshare.service.MyFavoritesService
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.viewmodel.ShowShareRegisterUserVM
import nl.bethamil.showshare.viewmodel.ShowShareUserVM
import org.hibernate.bytecode.BytecodeLogging.LOGGER
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest


@Controller
class UserController(
    val passwordEncoder: PasswordEncoder, val showShareUserService: ShowShareUserService,
    val captchaService: CaptchaService, val favoritesService: MyFavoritesService
) {

    @GetMapping("/login")
    protected fun login(): String {
        return "loginPage"
    }

    @GetMapping("/register")
    protected fun register(model: Model): String {
        model.addAttribute("newUser", ShowShareRegisterUserVM())
        return "registerPage"
    }

    @GetMapping("/myProfile")
    protected fun myProfile(model: Model, request: HttpServletRequest): String {
        val loggedInUser = getLoggedInUser(request)
        val first5: Pageable = PageRequest.of(0, 3, Sort.by("localDateTime"))
        val second5: Pageable = PageRequest.of(1, 3, Sort.by("localDateTime"))
        val third5: Pageable = PageRequest.of(2, 3, Sort.by("localDateTime"))
        val listOfUserShows1: List<Show> = getFavoriteShows(loggedInUser, first5)
        val listOfUserShows2: List<Show> = getFavoriteShows(loggedInUser, second5)
        val listOfUserShows3: List<Show> = getFavoriteShows(loggedInUser, third5)

        model.addAttribute("user", loggedInUser)
        model.addAttribute("shows1", listOfUserShows1)
        model.addAttribute("shows2", listOfUserShows2)
        model.addAttribute("shows3", listOfUserShows3)
        return "profilePage"
    }

    @GetMapping("/profile/{userId}")
    protected fun profile(model: Model, request: HttpServletRequest, @PathVariable userId: Long): String {
        val user = showShareUserService.findById(userId)!!

        val first5: Pageable = PageRequest.of(0, 3, Sort.by("localDateTime"))
        val second5: Pageable = PageRequest.of(1, 3, Sort.by("localDateTime"))
        val third5: Pageable = PageRequest.of(2, 3, Sort.by("localDateTime"))
        val listOfUserShows1: List<Show> = getFavoriteShows(user, first5)
        val listOfUserShows2: List<Show> = getFavoriteShows(user, second5)
        val listOfUserShows3: List<Show> = getFavoriteShows(user, third5)

        model.addAttribute("user", user)
        model.addAttribute("shows1", listOfUserShows1)
        model.addAttribute("shows2", listOfUserShows2)
        model.addAttribute("shows3", listOfUserShows3)
        return "profilePage"
    }

    @PostMapping("/profile/addAboutMe")
    protected fun addAboutMe(@ModelAttribute("aboutMe") aboutMe: String, request: HttpServletRequest): String {
        val user = showShareUserService.findByRegisteredUsername(request.userPrincipal.name)
        if (aboutMe.trim() == "") {
            user!!.aboutMe = null
        } else {
            user!!.aboutMe = aboutMe.trim()
        }
        showShareUserService.save(user)
        return "redirect:/myProfile"
    }

    private fun getFavoriteShows(
        loggedInUser: ShowShareUserVM,
        first5: Pageable
    ): List<Show> {
        val showList = favoritesService.findByUserId(loggedInUser.id!!, first5)
        val listOfUserShows: List<Show> = showList.map {
            MovieDbRestService().getSingleShow(it.showId.toInt())!!
        }
        return listOfUserShows
    }

    @PostMapping("/register/new")
    protected fun newUser(
        @ModelAttribute("newUser") showShareUser: ShowShareRegisterUserVM,
        @ModelAttribute("retypedPassword") checkPW: String, result: BindingResult,
        request: HttpServletRequest, model: Model
    ): String {
        if (!result.hasErrors()) {
            val newUser = ShowShareRegisterUserVM(
                username = showShareUser.username.trim(),
                email = showShareUser.email,
                password = passwordEncoder.encode(showShareUser.password)
            )
            val checkPassword = checkPassword(showShareUser.password!!, checkPW)
            val checkRecaptcha = checkRecaptcha(request)
            val checkUsername = checkUsername(showShareUser.username)
            val checkEmail = checkEmail(showShareUser.email)

            if (checkPassword && checkRecaptcha && checkUsername && checkEmail) {
                showShareUserService.save(newUser)
                authWithHttpServletRequest(
                    request = request, username = newUser.username,
                    password = showShareUser.password
                )
            } else {
                return addErrors(model, checkPassword, checkRecaptcha, checkUsername, checkEmail)
            }
        }
        return "redirect:/"
    }

    private fun addErrors(
        model: Model,
        checkPassword: Boolean,
        checkRecaptcha: Boolean,
        checkUsername: Boolean,
        checkEmail: Boolean
    ): String {
        model.addAttribute("showAlertScreen", true)
        model.addAttribute("notSamePassword", checkPassword)
        model.addAttribute("noFailRecaptcha", checkRecaptcha)
        model.addAttribute("noFailUsername", checkUsername)
        model.addAttribute("noFailEmail", checkEmail)
        return "registerPage"
    }

    fun authWithHttpServletRequest(request: HttpServletRequest, username: String?, password: String?) {
        try {
            request.session
            request.login(username, password)
        } catch (e: ServletException) {
            LOGGER.error("Error while login ", e)
        }
    }

    fun checkRecaptcha(request: HttpServletRequest): Boolean {
        val response = request.getParameter("g-recaptcha-response")
        val captcha = Captcha(System.getenv("recaptchaSecret"), response)
        return captchaService.verify(captcha)!!.success
    }

    fun checkPassword(password: String, pwCheck: String): Boolean {
        return password == pwCheck
    }

    fun checkUsername(name: String): Boolean {
        return showShareUserService.findByUsername(name) == null
    }

    fun checkEmail(email: String): Boolean {
        return showShareUserService.findByEmail(email) == null
    }

    private fun getLoggedInUser(request: HttpServletRequest): ShowShareUserVM {
        return showShareUserService.findByUsername(request.userPrincipal.name)!!
    }
}