package nl.bethamil.showshare.model

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.*


@Entity
class ShowShareUser(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    var id: Long? = null,

    @Column(name= "username", unique = true )
    private var username: String = "",
    private var password : String? = ""
)

    : UserDetails {

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val authorityList: MutableList<GrantedAuthority> = ArrayList()
        authorityList.add(SimpleGrantedAuthority("ROLE_USER"))
        return authorityList
    }

    override fun getPassword(): String {
        if (password != null) {
            return password.toString()
        }
        else return ""

    }

    override fun getUsername(): String {
        return username
    }
    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true

    }

    override fun isCredentialsNonExpired(): Boolean {
        return true

    }

    override fun isEnabled(): Boolean {
        return true
    }
}

