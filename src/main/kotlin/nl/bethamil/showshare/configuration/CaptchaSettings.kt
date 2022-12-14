package nl.bethamil.showshare.configuration

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component


@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
class CaptchaSettings {
    var site: String? = null
    var secret: String? = null
}