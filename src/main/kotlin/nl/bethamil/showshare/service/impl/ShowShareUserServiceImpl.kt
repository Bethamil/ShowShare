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
}