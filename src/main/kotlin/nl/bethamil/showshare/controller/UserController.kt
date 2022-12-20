package nl.bethamil.showshare.controller

import nl.bethamil.showshare.model.ShowShareUser
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
class UserController(val passwordEncoder: PasswordEncoder, val showShareUserService: ShowShareUserService) {

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
        @ModelAttribute("newUser") showShareUser: ShowShareRegisterUserVM, result: BindingResult,
        request: HttpServletRequest, model: Model
    ): String {
        if (!result.hasErrors()) {
            val newUser = ShowShareRegisterUserVM(
                username = showShareUser.username.trim(),
                password = passwordEncoder.encode(showShareUser.password)
            )
            try {
                showShareUserService.save(newUser)
            } catch (_: Exception) {
                model.addAttribute("newUser", ShowShareUser())
                model.addAttribute("alreadyInDb", true)
                return "registerPage"
            }
            authWithHttpServletRequest(request = request, username = newUser.username, password = showShareUser.password)
        }
        return "redirect:/"
    }

    fun authWithHttpServletRequest(request: HttpServletRequest, username: String?, password: String?) {
        try {
            request.session
            request.login(username, password)
        } catch (e: ServletException) {
            LOGGER.error("Error while login ", e)
        }
    }
}