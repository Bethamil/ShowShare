package nl.bethamil.showshare.controller

import nl.bethamil.showshare.reCaptcha.Captcha
import nl.bethamil.showshare.service.CaptchaService
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.viewmodel.ShowShareRegisterUserVM
import org.hibernate.bytecode.BytecodeLogging.LOGGER
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest


@Controller
class UserController(
    val passwordEncoder: PasswordEncoder, val showShareUserService: ShowShareUserService,
    val captchaService: CaptchaService
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

    @PostMapping("/register/new")
    protected fun newUser(
        @ModelAttribute("newUser") showShareUser: ShowShareRegisterUserVM,
        @ModelAttribute("retypedPassword") checkPW: String, result: BindingResult,
        request: HttpServletRequest, model: Model
    ): String {
        if (!result.hasErrors()) {
            val newUser = ShowShareRegisterUserVM(username = showShareUser.username.trim(),
                password = passwordEncoder.encode(showShareUser.password)
            )
            val checkPassword = checkPassword(showShareUser.password!!, checkPW)
            val checkRecaptcha = checkRecaptcha(request)
            val checkUsername = checkUsername(showShareUser.username)

            if (checkPassword && checkRecaptcha && checkUsername) {
                showShareUserService.save(newUser)
                authWithHttpServletRequest(request = request, username = newUser.username,
                    password = showShareUser.password
                )
            } else {
                return addErrors(model, checkPassword, checkRecaptcha, checkUsername)
            }
        }
        return "redirect:/"
    }

    private fun addErrors(
        model: Model,
        checkPassword: Boolean,
        checkRecaptcha: Boolean,
        checkUsername: Boolean
    ): String {
        model.addAttribute("showAlertScreen", true)
        model.addAttribute("notSamePassword", checkPassword)
        model.addAttribute("noFailRecaptcha", checkRecaptcha)
        model.addAttribute("noFailUsername", checkUsername)
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
}