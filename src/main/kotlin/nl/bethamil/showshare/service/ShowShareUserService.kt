package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.ShowShareRegisterUserVM
import nl.bethamil.showshare.viewmodel.ShowShareUserVM

interface ShowShareUserService {
    fun save(user : ShowShareRegisterUserVM)
    fun findByUsername(username: String?): ShowShareUserVM?

}