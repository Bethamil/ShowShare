package nl.bethamil.showshare.model

import javax.persistence.*

@Entity
data class ShowCollection(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    val user : ShowShareUser,

    val showId : Long
)
