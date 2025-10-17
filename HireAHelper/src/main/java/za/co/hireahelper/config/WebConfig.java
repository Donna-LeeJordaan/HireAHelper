package za.co.hireahelper.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class WebConfig {

    @Autowired
    private AuthenticationRedirect authenticationRedirect;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",
                                "/public/",
                                "/error",
                                "/favicon.ico",
                                "/auth/**",
                                "/HireAHelper/auth/**",
                                "/HireAHelper/client/create",
                                "/HireAHelper/client/register",
                                "/HireAHelper/serviceProvider/create",
                                "/HireAHelper/serviceProvider/register",
                                "/HireAHelper/area/all",
                                "/area/**",
                                "/HireAHelper/serviceType/all",
                                "/serviceType/**",
                                "/HireAHelper/serviceProvider/**",
                                "/HireAHelper/serviceProvider/all",
                                "/serviceProvider/**",
                                "/HireAHelper/client/**",
                                "/client/**",
                                "/HireAHelper/booking/**",
                                "/HireAHelper/booking/create",
                                "/booking/**",
                                "/HireAHelper/client/read/${userId}",
                                "/HireAHelper/client/read/**",

                                "/login",
                                "/HireAHelper/login"
                        ).permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/").permitAll()
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginProcessingUrl("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(authenticationRedirect)
                        .permitAll()
                )

                .httpBasic(basic -> basic.disable())

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        configuration.setAllowedHeaders(List.of("Authorization", "Content-Type", "Accept", "Origin",
                "X-Requested-With", "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        configuration.setExposedHeaders(List.of("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}