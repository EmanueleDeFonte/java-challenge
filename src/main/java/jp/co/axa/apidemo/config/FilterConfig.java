package jp.co.axa.apidemo.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for registering the LoggingFilter.
 */
@Configuration
public class FilterConfig {

    /**
     * Creates a FilterRegistrationBean for the LoggingFilter.
     *
     * @return the FilterRegistrationBean for the LoggingFilter
     */
    @Bean
    public FilterRegistrationBean<LoggingFilter> loggingFilterFilterRegistrationBean() {

        // Register the LoggingFilter so it can be used.
        FilterRegistrationBean<LoggingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new LoggingFilter());
        registrationBean.addUrlPatterns("/api/v1");
        return registrationBean;

    }

}
