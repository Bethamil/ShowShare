package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.mapper.ModelViewMapper
import nl.bethamil.showshare.repository.ShowShareUserRepo
import nl.bethamil.showshare.service.ShowShareUserService
import nl.bethamil.showshare.viewmodel.ShowShareRegisterUserVM
import nl.bethamil.showshare.viewmodel.ShowShareUserVM
import org.springframework.stereotype.Service

@Service
class ShowShareUserServiceImpl(val showShareUserRepo: ShowShareUserRepo) : ShowShareUserService, ModelViewMapper {
    override fun save(user: ShowShareRegisterUserVM)  {
            showShareUserRepo.save(user.toModel())
    }

    override fun findByUsername(username: String?): ShowShareUserVM? {
        val showShareUser =  showShareUserRepo.findByUsername(username)
        return if (showShareUser.isPresent) {
            showShareUser.get().toVM()
        } else null
    }

    override fun findByRegisteredUsername(username: String?): ShowShareRegisterUserVM? {
        val showShareUser =  showShareUserRepo.findByUsername(username)
        return if (showShareUser.isPresent) {
            showShareUser.get().toVMRegistered()
        } else null
    }

    override fun findByEmail(email: String?): ShowShareUserVM? {
        val showShareUser = showShareUserRepo.findByEmail(email)
        return if (showShareUser.isPresent) {
            showShareUser.get().toVM()
        } else null
    }

    override fun findById(userId: Long): ShowShareUserVM? {
        val user = showShareUserRepo.findById(userId)
        return if (user.isPresent) {
            user.get().toVM()
        } else null
    }
}