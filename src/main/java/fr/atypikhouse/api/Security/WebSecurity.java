package fr.atypikhouse.api.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Creer un filtre d'authentification & changer son url de login par defaut
        JWTAuthenticationFilter authFilter = new JWTAuthenticationFilter(authenticationManager());
        authFilter.setFilterProcessesUrl("/api/user/login");

        http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
            .cors()
            .and()
            .csrf()
            .disable()
            .authorizeRequests()
            // Autoriser des urls
            .antMatchers(HttpMethod.POST, "/api/user/create").permitAll()
            .antMatchers(HttpMethod.GET, "/api/location").permitAll()
            .antMatchers(HttpMethod.GET, "/api/location/{id}").permitAll()
            .antMatchers(HttpMethod.GET, "/api/equipement").permitAll()
            .antMatchers(HttpMethod.GET, "/api/commentaire").permitAll()
            // Autoriser le reste des URL juste pour les utilisateurs authentifieq
            .anyRequest().authenticated()
            .and()
            .addFilter(authFilter)
            .addFilter(new JWTAuthorizationFilter(authenticationManager()))
            // Desactiver les sessions
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // Specifier le crypteur des mots de passes
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
        return source;
    }
}