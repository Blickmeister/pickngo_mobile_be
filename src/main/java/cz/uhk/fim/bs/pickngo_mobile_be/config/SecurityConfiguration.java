package cz.uhk.fim.bs.pickngo_mobile_be.config;


import cz.uhk.fim.bs.pickngo_mobile_be.CustomUser.CustomOidcUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.CachingUserDetailsService;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SimpleSavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final CustomOidcUserService customOidcUserService;

    @Autowired
    public SecurityConfiguration(CustomOidcUserService customOidcUserService){
        this.customOidcUserService=customOidcUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

      //  http
      //          .oauth2Login()
      //          .and()
      //          .csrf()
      //          .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      //          .and()
      //          .authorizeRequests()
      //          .antMatchers("/**/*.{js,html,css}").permitAll()
      //          .antMatchers("/", "/api/user").permitAll()
      //          .anyRequest().authenticated();

        http.authorizeRequests().anyRequest().authenticated().and().oauth2Login().userInfoEndpoint().oidcUserService(customOidcUserService);

    }

    @Bean
    public RequestCache refererRequestCache() {
        return new HttpSessionRequestCache() {
            @Override
            public void saveRequest(HttpServletRequest request, HttpServletResponse response) {
                String referrer = request.getHeader("referer");
                if (referrer != null) {
                    request.getSession().setAttribute("SPRING_SECURITY_SAVED_REQUEST", new SimpleSavedRequest(referrer));
                }
            }
        };
    }

}