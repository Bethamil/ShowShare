package nl.bethamil.showshare.reCaptcha

import java.sql.Timestamp

data class CaptchaResponse(val success : Boolean, val challenge_ts : Timestamp, val hostname : String)