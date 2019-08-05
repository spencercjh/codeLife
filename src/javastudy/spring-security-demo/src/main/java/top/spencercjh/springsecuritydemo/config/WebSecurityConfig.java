package top.spencercjh.springsecuritydemo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

/**
 * @author Spencer
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig implements WebFluxConfigurer {
    final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    PasswordEncoder passwordEncoder;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        logger.info("WebFlux Security begin");
        return http.
                authorizeExchange().
                pathMatchers("/admin").hasAuthority("ROLE_ADMIN").
                pathMatchers("/superAdmin").hasAuthority("ROLE_SUPER_ADMIN").
                anyExchange().
                authenticated().
                and().
                addFilterAt(getBasicAuthenticationFilter(), SecurityWebFiltersOrder.HTTP_BASIC).
                authorizeExchange().
                pathMatchers("/api/**").
                authenticated().
                and().
                addFilterAt(bearerAuthenticationFilter(), SecurityWebFiltersOrder.AUTHENTICATION).
                formLogin().
                and().
                logout().
                and().
                build();
    }

    public ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        UserDetails user = User.
                withUsername("user").
                password(passwordEncoder().encode("123456")).
                roles("USER").
                build();
        UserDetails admin = User.withUsername("admin").
                password(passwordEncoder().encode("admin")).
                roles("ADMIN").
                build();
        UserDetails superAdmin = User.withUsername("superAdmin").
                password(passwordEncoder().encode("admin")).
                roles("SUPER_ADMIN").
                build();
        return new MapReactiveUserDetailsService(user, admin, superAdmin);
    }

    private AuthenticationWebFilter getBasicAuthenticationFilter() {
        UserDetailsRepositoryReactiveAuthenticationManager authManager;
        AuthenticationWebFilter basicAuthenticationFilter;
        ServerAuthenticationSuccessHandler successHandler;
        authManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService());
        successHandler = new BasicAuthenticationSuccessHandler();
        basicAuthenticationFilter = new AuthenticationWebFilter(authManager);
        basicAuthenticationFilter.setAuthenticationSuccessHandler(successHandler);
        return basicAuthenticationFilter;
    }

    private AuthenticationWebFilter bearerAuthenticationFilter() {
        AuthenticationWebFilter bearerAuthenticationFilter;
        Function<ServerWebExchange, Mono<Authentication>> bearerConverter;
        ReactiveAuthenticationManager authManager;

        authManager = new TokenReactiveAuthenticationManager();
        bearerAuthenticationFilter = new AuthenticationWebFilter(authManager);
        bearerConverter = new ServerHttpBearerAuthenticationConverter();

        bearerAuthenticationFilter.setAuthenticationConverter(bearerConverter);
        bearerAuthenticationFilter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/api/**"));

        return bearerAuthenticationFilter;
    }
}