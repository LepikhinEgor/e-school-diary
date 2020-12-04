package com.scw.electronicgradebook.config;

import com.scw.electronicgradebook.services.impl.CustomCsrfTokenRepository;
import com.scw.electronicgradebook.services.impl.HttpFloodFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private HttpFloodFilter floodFilter;

    private CustomCsrfTokenRepository csrfTokenRepository;

    @Autowired
    public void setFloodFilter(HttpFloodFilter floodFilter) {
        this.floodFilter = floodFilter;
    }

    @Autowired
    public void setCsrfTokenRepository(CustomCsrfTokenRepository csrfTokenRepository) {
        this.csrfTokenRepository = csrfTokenRepository;
    }

    @Bean
    public PasswordEncoder argonEncoder() {
        return new Argon2PasswordEncoder(
                128, //salt length
                128, //hash length
                8, //parallel
                65536 / 4, //memory
                8); //iterations
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .csrfTokenRepository(csrfTokenRepository)
                .and()
//                .disable()
                .addFilterBefore(floodFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").hasAuthority("SWAGGER_ACCESS")
                .and()
                .formLogin().loginPage("/auth/login")
                .defaultSuccessUrl("/main")
                .failureUrl("/auth/login?error")
                .and()
                .authorizeRequests()
                .antMatchers(Endpoints.DEV_END_POINTS).denyAll()
                .and()
                .requiresChannel().anyRequest().requiresSecure()
                .and()
                .portMapper()
                .http(8080).mapsTo(8443);
    }
}
