package it.uniroma3.siw.authentication;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration {

	@Autowired
	private
	DataSource dataSource;
	
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	    auth.jdbcAuthentication()
	        .dataSource(dataSource)
	        .authoritiesByUsernameQuery("SELECT email, role FROM credentials WHERE email=?")
	        .usersByUsernameQuery("SELECT email, password, 1 as enabled FROM credentials WHERE email=?");
	}
	
	
	@Bean
	 PasswordEncoder passwordEncoder() {
	    return new BCryptPasswordEncoder(); 
	}
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
	    httpSecurity
	        .csrf(csrf -> csrf.disable())
	        .authorizeHttpRequests(authorizeRequests ->
	            authorizeRequests
	                .requestMatchers(HttpMethod.GET, "/**", "/index","/ricette","/cuochi","/foundCuochi","/foundRicette","/newRicetta","/ricetta/{id}","/cuoco","/cuoco/{id}","/registration", "/register", "/css/**", "/images/**").permitAll()
	                .requestMatchers(HttpMethod.POST, "/registration", "/login").permitAll()
	                .requestMatchers(HttpMethod.GET, "/admin/**").hasAuthority(ADMIN_ROLE)
	                .requestMatchers(HttpMethod.POST, "/admin/**").hasAuthority(ADMIN_ROLE)
	                .anyRequest().authenticated()
	        )
	        .formLogin(formLogin ->
	            formLogin
	                .loginPage("/login")
	                .permitAll()
	                .defaultSuccessUrl("/success", true)
	                .failureUrl("/login?error=true")
	        )
	        .logout(logout ->
	            logout
	                .logoutUrl("/logout")
	                .logoutSuccessUrl("/")
	                .invalidateHttpSession(true)
	                .deleteCookies("JSESSIONID")
	                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
	                .clearAuthentication(true)
	                .permitAll()
	        );

	    return httpSecurity.build();
	}

	
	
	
}
