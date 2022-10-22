package nl.bethamil.showshare.model

import javax.persistence.*

@Entity
class RatingShow(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id", nullable = false)
    var ratingId: Long? = null,

    @ManyToOne
    val user : ShowShareUser? = null,
    val rating : Double? = null,
    val showId: Long? = null,
) {


}
