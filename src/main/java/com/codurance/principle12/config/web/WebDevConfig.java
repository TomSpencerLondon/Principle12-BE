package com.codurance.principle12.config.web;

import static java.util.Collections.singletonList;

import com.codurance.principle12.config.Environment;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile(Environment.DEV)
public class WebDevConfig {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  @Configuration
  public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
      return CorsFilterConfiguration.simpleCorsFilter(singletonList("http://localhost:3000"));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
      registry.addInterceptor(new GoogleTokenAuthenticator());
    }
  }
}
