package pairing.ideal.member.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import pairing.ideal.member.jwt.JwtFilter;
import pairing.ideal.member.service.CustomUserDetailService;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {
    private final JwtFilter jwtFilter;
    private final CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable);
        http.csrf(AbstractHttpConfigurer::disable);
        http.cors(AbstractHttpConfigurer::disable);
        http.userDetailsService(customUserDetailService);
        http.addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class);
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers(
                                "/**"
                        )
                        .permitAll()
                        .anyRequest()
                        .authenticated()
        );
        return http.build();
    }
}