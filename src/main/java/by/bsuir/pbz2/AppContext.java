package by.bsuir.pbz2;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@ComponentScan
@EnableTransactionManagement
@RequiredArgsConstructor
@EnableWebSocket
public class AppContext extends WebMvcConfigurationSupport implements WebSocketConfigurer {

    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        viewResolver.setViewClass(JstlView.class);
        return viewResolver;
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("css/**", "images/**", "scriptJS/**")
                .addResourceLocations("classpath:/static/css/", "classpath:/static/images/", "classpath:/static/scriptJS/");
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {
        return Persistence.createEntityManagerFactory("psql");
    }

    @Bean
    public TransactionManager transactionManager(EntityManagerFactory factory) {
        return new JpaTransactionManager(factory);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new ScreenShareHandler(), "events/screen-share/{accessKey}")
                .setAllowedOrigins("*");
        registry.addHandler(new ChatWebSocketHandler(), "/chat/{accessKey}")
                .setAllowedOrigins("*"); // Разрешаем все источники
    }
}
