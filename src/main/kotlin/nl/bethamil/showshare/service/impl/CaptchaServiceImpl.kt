package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.reCaptcha.Captcha
import nl.bethamil.showshare.reCaptcha.CaptchaResponse
import nl.bethamil.showshare.service.CaptchaService
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.net.URI

@Service
class CaptchaServiceImpl : CaptchaService {
    override fun verify(captcha: Captcha): CaptchaResponse? {
        val restTemplate = RestTemplate()
        val url: URI =
            URI.create("https://www.google.com/recaptcha/api/siteverify?" +
                    "secret=${captcha.secret}&response=${captcha.response}")
        return restTemplate.getForObject(url, CaptchaResponse::class.java)
    }
}