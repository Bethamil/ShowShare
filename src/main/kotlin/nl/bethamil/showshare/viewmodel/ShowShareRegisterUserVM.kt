package nl.bethamil.showshare.viewmodel

data class ShowShareRegisterUserVM(
    var id: Long? = null,
    var username: String = "",
    var email: String = "",
    var aboutMe: String? = "",
    var password: String? = ""
)