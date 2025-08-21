package za.co.hireahelper;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SpringBootSecurityConfig {

    private final DataSource dataSource;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public SpringBootSecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Configure JDBC-based users
    @Bean
    public JdbcUserDetailsManager users() {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        // Query to fetch username, password, and enabled
        users.setUsersByUsernameQuery(
                "SELECT username, password, true FROM users WHERE username = ?"
        );

        // Query to fetch authorities/roles
        users.setAuthoritiesByUsernameQuery(
                "SELECT username, role FROM users WHERE username = ?"
        );

        return users;
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // disable CSRF for testing
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll() // open /auth endpoints
                        .anyRequest().authenticated()            // all others need auth
                )
                .httpBasic(Customizer.withDefaults());       // use basic auth

        return http.build();
    }
}
