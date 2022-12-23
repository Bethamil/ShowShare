package nl.bethamil.showshare.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class MyFavorites(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user : ShowShareUser,
    val showId : Long,
    val localDateTime: LocalDateTime
)
