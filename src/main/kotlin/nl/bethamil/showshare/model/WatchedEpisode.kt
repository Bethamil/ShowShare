package nl.bethamil.showshare.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class WatchedEpisode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watched_episode_id", nullable = false)
    var viewDateId: Long? = null,

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val showShareUser: ShowShareUser,
    val showId : Int,
    val seasonNumber : Int,
    val episodeNumber : Int
)

