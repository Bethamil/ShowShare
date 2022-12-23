package nl.bethamil.showshare.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDateTime
import javax.persistence.*

@Entity
class Watchlist (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watchlist_id", nullable = false)
    var watchlistId: Long? = null,
    val showId : Long,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val showShareUser: ShowShareUser,
    val localDateTime: LocalDateTime
)


