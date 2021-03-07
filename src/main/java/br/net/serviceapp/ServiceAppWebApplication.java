package br.net.serviceapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;


@SpringBootApplication
public class ServiceAppWebApplication extends WebSecurityConfigurerAdapter {
	
	private AuthenticationFailureHandler handler;

	public static void main(String[] args) {
		SpringApplication.run(ServiceAppWebApplication.class, args);
	}
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// @formatter:off
        http
            .authorizeRequests(a -> a
                .antMatchers("/", "/error", "/webjars/**", "/images/**", "/logout", "/webpushr-sw.js").permitAll()
                .anyRequest().authenticated()
            )
            .logout(l -> l
            		.logoutSuccessUrl("/").permitAll()
            )
            .csrf(c -> c
            		.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .exceptionHandling(e -> e
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
            )
            .oauth2Login(o -> o
                .failureHandler((request, response, exception) -> {
    			    request.getSession().setAttribute("error.message", exception.getMessage());
    			    handler.onAuthenticationFailure(request, response, exception);
                })
            );
        // @formatter:on
    }
}
