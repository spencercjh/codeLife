package top.spencercjh.springsecuritydemo.config.security;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.RedirectServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.authentication.logout.RedirectServerLogoutSuccessHandler;
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler;
import org.springframework.security.web.server.csrf.ServerCsrfTokenRepository;
import org.springframework.security.web.server.csrf.WebSessionServerCsrfTokenRepository;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.spencercjh.springsecuritydemo.service.JwtService;
import top.spencercjh.springsecuritydemo.util.SnowFlakeUtil;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Spencer
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig implements WebFluxConfigurer {
    final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtService jwtService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        logger.info("WebFlux Security begin");
        return http
                .authorizeExchange()
                .pathMatchers("/admin").hasAuthority("ROLE_ADMIN")
                .pathMatchers("/superAdmin").hasAuthority("ROLE_SUPER_ADMIN")
                .anyExchange()
                .authenticated()
                .and()
                .formLogin()
                .authenticationSuccessHandler(new loginSuccessHandler())
                .authenticationSuccessHandler(new RedirectServerAuthenticationSuccessHandler())
                .and()
                .logout()
                .and()
                .csrf()
                .csrfTokenRepository(csrfTokenRepository())
                .and()
                .httpBasic()
                .and()
                .build();
    }

    @Bean
    public ServerCsrfTokenRepository csrfTokenRepository() {
        WebSessionServerCsrfTokenRepository repository = new WebSessionServerCsrfTokenRepository();
        repository.setHeaderName("X-CSRF-TK");
        return repository;
    }

    private ServerLogoutSuccessHandler logoutSuccessHandler(String uri) {
        RedirectServerLogoutSuccessHandler successHandler = new RedirectServerLogoutSuccessHandler();
        successHandler.setLogoutSuccessUrl(URI.create(uri));
        return successHandler;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        UserDetails user = User
                .withUsername("user")
                .password(passwordEncoder().encode("123456"))
                .roles("USER")
                .build();
        UserDetails admin = User
                .withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles("ADMIN")
                .build();
        UserDetails superAdmin = User
                .withUsername("superAdmin")
                .password(passwordEncoder().encode("admin"))
                .roles("SUPER_ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user, admin, superAdmin);
    }

    private class loginSuccessHandler implements ServerAuthenticationSuccessHandler {

        @Override
        public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
            Map<String, Object> authInfo = new HashMap<>(16);
            User user = (User) authentication.getPrincipal();
            authInfo.put("username", user.getUsername());
            authInfo.put("password", user.getPassword());
            authInfo.put("role", user.getAuthorities());
            ServerWebExchange exchange = webFilterExchange.getExchange();
            exchange.getResponse()
                    .getHeaders()
                    // fixme it not works!
                    .add(HttpHeaders.AUTHORIZATION, jwtService.createJwt(SnowFlakeUtil.getSessionId(), JSON.toJSONString(authInfo)));
            return webFilterExchange.getChain().filter(exchange);
        }
    }
}