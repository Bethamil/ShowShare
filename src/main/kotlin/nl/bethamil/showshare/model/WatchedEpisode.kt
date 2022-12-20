package nl.bethamil.showshare.model

import javax.persistence.*

@Entity
class WatchedEpisode(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "watched_episode_id", nullable = false)
    var viewDateId: Long? = null,

    @ManyToOne
    val showShareUser: ShowShareUser,
    val showId : Int,
    val seasonNumber : Int,
    val episodeNumber : Int
)

