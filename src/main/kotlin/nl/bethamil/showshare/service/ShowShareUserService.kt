package nl.bethamil.showshare.service

import nl.bethamil.showshare.model.ShowShareUser
import java.util.*

interface ShowShareUserService {
    fun save(user : ShowShareUser)
    fun findByUsername(username: String?): Optional<ShowShareUser>

}