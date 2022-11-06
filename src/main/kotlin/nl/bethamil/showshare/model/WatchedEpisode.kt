package nl.bethamil.showshare.model

import javax.persistence.*

@Entity
data class WatchedEpisode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watched_episode_id", nullable = false)
    open var viewDateId: Long? = null,

    @ManyToOne
    val showShareUser: ShowShareUser,
    val showId : Int,
    val seasonNumber : Int,
    val episodeNumber : Int
)

