package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("bootstrap_js/login");
        registry.addViewController("/login*").setViewName("bootstrap_js/login");
        registry.addViewController("/user").setViewName("bootstrap_js/user");
        registry.addViewController("/admin").setViewName("bootstrap_js/admin");
    }
}
