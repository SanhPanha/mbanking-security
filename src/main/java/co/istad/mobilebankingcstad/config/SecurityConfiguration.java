package co.istad.mobilebankingcstad.config;

import co.istad.mobilebankingcstad.security.CustomUserDetailsService;
import co.istad.mobilebankingcstad.security.JwtToUserConverter;
import co.istad.mobilebankingcstad.security.KeyUtils;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtToUserConverter jwtToUserConverter;
    private final KeyUtils keyUtils;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // configure securityfilter chain
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        (authz) -> authz
                                // allow all resources regarding  swagger ui
                                .requestMatchers("/",
                                        "/v3/api-docs/**",
                                        "/swagger-ui/**",
                                        "/v2/api-docs/**",
                                        "/swagger-resources/**"
                                )
                                .permitAll()
                                .requestMatchers("/api/v1/auth/**")
                                .permitAll()
                                // since user will need to upload the picture in order to register
                                .requestMatchers(
                                        "api/v1/files/**",
                                        "images/**"
                                )
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .oauth2ResourceServer(
                        (oauth2) -> oauth2.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtToUserConverter))
                )
                .sessionManagement(
                        (session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .exceptionHandling(
                        (ex) -> ex.authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
                )
                .build();
    }

    // related to jwtEncoder and Decoder
    @Bean
    @Qualifier("jwtRefreshTokenEncoder")
    JwtEncoder jwtRefreshTokenEncoder(){
        JWK jwk = new RSAKey.Builder(keyUtils.getRefreshTokenPublicKey())
                .privateKey(keyUtils.getRefreshTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Qualifier("jwtRefreshTokenDecoder")
    JwtDecoder jwtRefreshTokenDecoder(){
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getRefreshTokenPublicKey())
                .build();
    }

    @Bean
    @Primary
    JwtEncoder jwtAccessTokenEncoder(){
        JWK jwk = new RSAKey.Builder(keyUtils.getAccessTokenPublicKey())
                .privateKey(keyUtils.getAccessTokenPrivateKey())
                .build();
        JWKSource<SecurityContext> jwkSource = new ImmutableJWKSet<>(new JWKSet(jwk));
        return new NimbusJwtEncoder(jwkSource);
    }
    @Bean
    @Primary
    JwtDecoder jwtAccessTokenDecoder(){
        return NimbusJwtDecoder
                .withPublicKey(keyUtils.getAccessTokenPublicKey())
                .build();
    }

    @Bean
    @Qualifier("refreshTokenAuthProvider")
    JwtAuthenticationProvider refreshTokenAuthProvider(){
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(
                jwtRefreshTokenDecoder()
        );
        provider.setJwtAuthenticationConverter(jwtToUserConverter);
        return provider;

    }

}
