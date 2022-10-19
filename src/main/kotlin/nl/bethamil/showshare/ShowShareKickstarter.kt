package nl.bethamil.showshare

import nl.bethamil.showshare.model.ShowShareUser
import nl.bethamil.showshare.repository.ShowShareUserRepo
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootApplication
class ShowShareKickstarter(
    val showShareUserRepo: ShowShareUserRepo,
    val passwordEncoder: PasswordEncoder,

    ) : CommandLineRunner {


    override fun run(vararg args: String?) {
        if (showShareUserRepo.findByUsername("admin")?.isEmpty == true) {
            var admin = ShowShareUser(username = "admin",
                password = passwordEncoder.encode("admin"))
            showShareUserRepo.save(admin)
        }
    }
}