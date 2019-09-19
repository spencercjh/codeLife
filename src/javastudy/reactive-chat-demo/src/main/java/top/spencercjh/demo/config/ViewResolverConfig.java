package top.spencercjh.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.ViewResolverRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.thymeleaf.spring5.view.reactive.ThymeleafReactiveViewResolver;

/**
 * @author Spencer
 * reference: https://blog.csdn.net/fly_leopard/article/details/88355349
 */
@Configuration
public class ViewResolverConfig implements WebFluxConfigurer {
    /**
     * 引入spring-boot-starter-thymeleaf自动会注入该bean
     */
    private final ThymeleafReactiveViewResolver thymeleafReactiveViewResolver;

    public ViewResolverConfig(ThymeleafReactiveViewResolver thymeleafReactiveViewResolver) {
        this.thymeleafReactiveViewResolver = thymeleafReactiveViewResolver;
    }

    /**
     * 加入thymeleaf试图解析器，不然找不到view name
     * @param registry webflux view registry
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(thymeleafReactiveViewResolver);
    }

    /**
     * 映射静态资源文件映射
     * @param registry webflux resource registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                //指向的是目录
                .addResourceLocations("classpath:/static/");
    }
}
