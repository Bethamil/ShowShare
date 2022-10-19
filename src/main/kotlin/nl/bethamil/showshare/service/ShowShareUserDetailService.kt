package nl.bethamil.showshare.service


import nl.bethamil.showshare.repository.ShowShareUserRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class ShowShareUserDetailService(val showShareUserRepo: ShowShareUserRepo) : UserDetailsService  {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String?): UserDetails? {
        return showShareUserRepo.findByUsername(username)?.orElseThrow {
            UsernameNotFoundException(
                String.format(
                    "User with name %s not found",
                    username
                )
            )
        }
    }
}


