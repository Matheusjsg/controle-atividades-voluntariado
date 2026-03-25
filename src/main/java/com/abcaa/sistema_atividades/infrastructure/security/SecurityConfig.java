package com.abcaa.sistema_atividades.infrastructure.security;

import com.abcaa.sistema_atividades.business.repositories.VolunteerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final VolunteerRepository volunteerRepository;

    public SecurityConfig(JwtFilter jwtFilter, VolunteerRepository volunteerRepository) {
        this.jwtFilter = jwtFilter;
        this.volunteerRepository = volunteerRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return email -> volunteerRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado."));
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/volunteer/list").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/departments/list").hasAnyRole("ADMIN", "VOLUNTEER")
                        .requestMatchers(HttpMethod.GET, "/volunteer/profile").hasAnyRole("ADMIN", "VOLUNTEER")
                        .requestMatchers(HttpMethod.GET, "/volunteer/profile/{volunteerId}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/volunteer/profile").hasAnyRole("ADMIN", "VOLUNTEER")
                        .requestMatchers("/volunteer/update/**", "/volunteer/delete/**", "/volunteer/{id}").hasRole("ADMIN")
                        .requestMatchers("/departments/create", "/departments/delete/**", "/departments/update/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/activity/*/status").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/activity/report/**").hasAnyRole("ADMIN", "VOLUNTEER")
                        .requestMatchers(HttpMethod.GET, "/certificate/generate/**").authenticated()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
        
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
