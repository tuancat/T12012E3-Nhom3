package com.sam.lab4;

import com.sam.lab4.service.CustomeUserDetailService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.InMemoryTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

@Configuration
@ComponentScan({"com.sam"})
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    PersistentTokenRepository tokenRepository=new InMemoryTokenRepositoryImpl();
    @Qualifier("customUserDetailService")
    private CustomeUserDetailService customeUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customeUserDetailService).passwordEncoder(passwordEncoder());
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/","/login","/user/login", "/user/logout", "/logout", "/mail/**").permitAll();
        http.authorizeRequests().antMatchers("/user/**").access("hasAnyRole('ROLE_USER')");

        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/user/login")//
                .defaultSuccessUrl("/user/user-list")//
                .failureUrl("/user/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password").and().rememberMe().rememberMeParameter("remember-me")
                .tokenRepository(tokenRepository).tokenValiditySeconds(1 * 24 * 60 * 60)
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl("/user/logout")
                .and().exceptionHandling().accessDeniedPage("/user/Access_Denied");
    }

}
