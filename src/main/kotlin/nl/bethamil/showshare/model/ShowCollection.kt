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
) {
    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , user = $user , showId = $showId )"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ShowCollection

        if (id != other.id) return false
        if (user != other.user) return false
        if (showId != other.showId) return false

        return true
    }
}
