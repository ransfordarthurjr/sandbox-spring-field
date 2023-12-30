package tech.hephaestusforge.messaging.antenna.configuration.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApplicationSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .cors(httpSecurityCorsConfigurer -> corsConfigurationSource())
                .headers(headers -> {
                    headers.httpStrictTransportSecurity(hstsConfig -> {
                        hstsConfig.includeSubDomains(true);
                        hstsConfig.maxAgeInSeconds(31536000);
                    });
                    headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::deny);
                    headers.xssProtection(xssConfig ->
                            xssConfig.headerValue(XXssProtectionHeaderWriter.HeaderValue.DISABLED));
                    headers.contentSecurityPolicy(contentSecurityPolicyConfig ->
                            contentSecurityPolicyConfig.policyDirectives("default-src 'none'"));
                    headers.crossOriginResourcePolicy(crossOriginResourcePolicyConfig -> this.corsMappingConfigurer());
                })
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return httpSecurity.build();
    }

    @Bean
    public WebMvcConfigurer corsMappingConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@SuppressWarnings("NullableProblems") CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*", "**")
                        .allowedMethods(HttpMethod.GET.name(), HttpMethod.POST.name())
                        .maxAge(3600)
                        .allowedHeaders(
                                "Accept",
                                "Accept-Encoding",
                                "Access-Control-Allow-Origin",
                                "Access-Control-Request-Headers",
                                "Access-Control-Request-Method",
                                "Authorization",
                                "Connection",
                                "Content-Length",
                                "Content-Type",
                                "Host",
                                "Origin",
                                "Origin Accept",
                                "User-Agent",
                                "X-Requested-With",
                                "x-source-code"
                        )
                        .exposedHeaders("Accept",
                                "Access-Control-Allow-Credentials",
                                "Access-Control-Allow-Origin",
                                "Authorization",
                                "Content-Length",
                                "Content-Type",
                                "Origin");
            }
        };
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(false);
        configuration.setAllowedOrigins(Arrays.asList("*", "**"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name()));
        configuration.setAllowedHeaders(Arrays.asList("Accept",
                "Accept-Encoding",
                "Access-Control-Allow-Origin",
                "Access-Control-Request-Headers",
                "Access-Control-Request-Method",
                "Authorization",
                "Connection",
                "Content-Length",
                "Content-Type",
                "Host",
                "Origin",
                "Origin Accept",
                "User-Agent",
                "X-Requested-With",
                "x-source-code"));
        configuration.setExposedHeaders(Arrays.asList("Accept",
                "Access-Control-Allow-Credentials",
                "Access-Control-Allow-Origin",
                "Authorization",
                "Content-Length",
                "Content-Type",
                "Origin"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
