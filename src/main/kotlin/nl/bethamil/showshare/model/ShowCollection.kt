package nl.bethamil.showshare.model

import org.hibernate.Hibernate
import javax.persistence.*

@Entity
data class ShowCollection(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long? = null,

    @ManyToOne
    val user : ShowShareUser,

    val showId : Long
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as ShowCollection

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , user = $user , showId = $showId )"
    }
}
