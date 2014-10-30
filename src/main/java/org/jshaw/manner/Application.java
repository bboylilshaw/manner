package org.jshaw.manner;

import org.jshaw.manner.domain.User;
import org.jshaw.manner.security.SpringSecurityAuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Arrays;
import java.util.Locale;
import java.util.Properties;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableJpaRepositories
@EnableJpaAuditing
public class Application extends WebMvcConfigurerAdapter {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        Arrays.asList(beanNames).forEach(System.out::println);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(Locale.US);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(465);
        mailSender.setUsername("");
        mailSender.setPassword("");
        mailSender.setProtocol("smtp");
        mailSender.setDefaultEncoding("UTF-8");

        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.port", "465");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.timeout", "15000");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.socketFactory.fallback", "false");

        mailSender.setJavaMailProperties(props);

        return mailSender;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean
    public AuditorAware<User> auditorAware() {
        return new SpringSecurityAuditorAwareImpl();
    }

//    @Bean
//    public EmbeddedServletContainerCustomizer containerCustomizer() {
//
//        return (container -> {
//            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "401");
//            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "404");
//            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "500");
//
//            container.addErrorPages(error401Page, error404Page, error500Page);
//        });
//    }

}
