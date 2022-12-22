package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.gravatar.MD5Util

data class ShowShareUserVM(
    var id: Long? = null,
    var username: String = "",
    var email: String = "",
    var aboutMe: String? = "",
    var gravatar: String? = MD5Util.md5Hex(email.lowercase())
)
