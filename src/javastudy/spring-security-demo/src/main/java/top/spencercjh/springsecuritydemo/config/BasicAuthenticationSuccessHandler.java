package top.spencercjh.springsecuritydemo.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import top.spencercjh.springsecuritydemo.util.SnowFlake;

/**
 * @author CAP5SGH
 */
public class BasicAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerWebExchange exchange = webFilterExchange.getExchange();
        exchange.getResponse()
                .getHeaders()
                .add(HttpHeaders.AUTHORIZATION, SnowFlake.getSessionId());
        System.out.println(exchange.getRequest().getHeaders().toSingleValueMap().toString());
        System.out.println(exchange.getResponse().getHeaders().toSingleValueMap().toString());
        return webFilterExchange.getChain().filter(exchange);
    }
}
