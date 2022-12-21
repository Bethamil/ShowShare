package nl.bethamil.showshare.service

import nl.bethamil.showshare.reCaptcha.Captcha
import nl.bethamil.showshare.reCaptcha.CaptchaResponse

interface CaptchaService {
    fun verify(captcha: Captcha) : CaptchaResponse?
}