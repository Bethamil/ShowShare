package nl.bethamil.showshare.service.userDetailService

import nl.bethamil.showshare.repository.ShowShareUserRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class ShowShareUserDetailService(val showShareUserRepo: ShowShareUserRepo) : UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        return try {
            showShareUserRepo.findByUsername(username).get()
        } catch (e: NoSuchElementException) {
            showShareUserRepo.findByEmail(username).orElseThrow {
                UsernameNotFoundException(
                    String.format(
                        "User with %s not found",
                        username
                    )
                )
            }
        }
    }
}


