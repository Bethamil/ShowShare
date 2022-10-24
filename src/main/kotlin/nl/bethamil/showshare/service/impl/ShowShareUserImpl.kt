package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.model.ShowShareUser
import nl.bethamil.showshare.repository.ShowShareUserRepo
import nl.bethamil.showshare.service.ShowShareUserService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShowShareUserImpl(val showShareUserRepo: ShowShareUserRepo) : ShowShareUserService {
    override fun save(user: ShowShareUser) {
        showShareUserRepo.save(user)
    }

    override fun findByUsername(username: String?): Optional<ShowShareUser> {
        return showShareUserRepo.findByUsername(username)
    }
}