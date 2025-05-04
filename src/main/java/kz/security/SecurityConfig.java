package kz.security;  // Use your correct package

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Custom firewall to allow more relaxed URL handling (e.g., allow "%0A" characters)
    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        return new DefaultHttpFirewall(); // Allow encoded characters like '%0A'
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Disable CSRF protection for stateless APIs
                .authorizeRequests()
                .antMatchers("/auth/register").permitAll();

        return http.build(); // Important to return the http.build() here!
    }
}
