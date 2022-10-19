package nl.bethamil.showshare.configuration

import nl.bethamil.showshare.service.ShowShareUserDetailService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
@Profile("!https")
class ShowShareSecurityConfig(val showShareUserDetailService: ShowShareUserDetailService) {

    @Bean
    @Throws(Exception::class)
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain? {
        httpSecurity.authorizeHttpRequests { authorize ->
            authorize
                .antMatchers("/css/*", "/webjars/**", "/images/**", "/search/**").permitAll()
                .antMatchers("/", "/populair/**", "/show/*", "/register/**").permitAll()
                .anyRequest().authenticated()
        }.formLogin { form: FormLoginConfigurer<HttpSecurity> -> form.loginPage("/login").permitAll().and() }
            .logout { logout: LogoutConfigurer<HttpSecurity?> ->
                logout.permitAll().invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID").logoutSuccessUrl("/")
            }
        httpSecurity.csrf().disable()
        return httpSecurity.build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder? {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun authenticationProvider(): DaoAuthenticationProvider? {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(showShareUserDetailService)
        authenticationProvider.setPasswordEncoder(passwordEncoder())
        return authenticationProvider
    }


}


