package com.example.Finanzas.Inteligentes.auth;

import com.example.Finanzas.Inteligentes.utils.Constants;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;


@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    /*Permisos solo a autorizados*/
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/public/**").permitAll()
                .antMatchers(HttpMethod.GET, "/carga/**").permitAll()
                .antMatchers(HttpMethod.GET, "/resources/**").permitAll()
                .antMatchers(HttpMethod.POST, "/public/**").permitAll()
                .antMatchers(HttpMethod.DELETE, "/public/**").permitAll()
                .antMatchers(HttpMethod.GET, "/resources/viewtoken/**").permitAll()
                .antMatchers(HttpMethod.GET, "/**").hasAnyAuthority("ROLE_ADMIN","ROLE_ANALISTA","ROLE_CLIENTE")
                //.antMatchers(HttpMethod.GET,"/usuario/getById/**").hasAnyAuthority("ROLE_CLIENTE")
                .anyRequest().authenticated()
                .and().cors().configurationSource(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList(Constants.frontEndApp));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corfFilter() {
        FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}
