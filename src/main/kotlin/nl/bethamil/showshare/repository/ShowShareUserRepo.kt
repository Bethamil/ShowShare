package nl.bethamil.showshare.repository

import nl.bethamil.showshare.model.ShowShareUser
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ShowShareUserRepo : JpaRepository<ShowShareUser, Long> {
    fun findByUsername(username: String?): Optional<ShowShareUser?>?
}