package nl.bethamil.showshare.model

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
    val showShareUser: ShowShareUser,
    val localDateTime: LocalDateTime
)


