package nl.bethamil.showshare.model

import javax.persistence.*

@Entity
class MyFavorites(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,
    @ManyToOne
    val user : ShowShareUser,
    val showId : Long
)
