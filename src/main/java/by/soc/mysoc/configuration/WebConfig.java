package by.soc.mysoc.configuration;

import by.soc.mysoc.interseptor.MainInterceptor;
import by.soc.mysoc.service.TokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@EnableWebMvc
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    private final TokenService tokenService;

    public WebConfig(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Bean
    public MainInterceptor mainInterceptor(){
        return new MainInterceptor(tokenService);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MainInterceptor(tokenService)).addPathPatterns("/**");
    }
}
