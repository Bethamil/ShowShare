package nl.bethamil.showshare.model

import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import javax.persistence.*

@Entity
class RatingShow(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rating_id", nullable = false)
    var ratingId: Long? = null,
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val user : ShowShareUser? = null,
    val rating : Double? = null,
    val showId: Long? = null,
) {

}
