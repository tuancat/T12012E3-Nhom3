package com.sam.lab4;

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
//    @Qualifier("customUserDetailService")
//    private CustomeUserDetailService customeUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder;
    }

//    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
////        auth.userDetailsService(customeUserDetailService).passwordEncoder(passwordEncoder());
//        auth.authenticationProvider(authenticationProvider());
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user1")
                .password("$2a$10$t91y6AKiCADSeED6JlFGveBNaClYhDj.bCxAKjzoRPK1HkYp9ejgy").roles("USER");
        auth.inMemoryAuthentication().withUser("admin")
                .password("admin").roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Các trang không yêu cầu login
        http.authorizeRequests().antMatchers("/","/login","/user/login", "/user/logout", "/logout").permitAll();
        http.authorizeRequests().antMatchers("/product/**", "/user/**").access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')");

        // Cấu hình cho Login Form.
        http.authorizeRequests().and().formLogin()//
                // Submit URL của trang login
                .loginProcessingUrl("/j_spring_security_check") // Submit URL
                .loginPage("/user/login")//
                .defaultSuccessUrl("/user/list")//
                .failureUrl("/user/login?error=true")//
                .usernameParameter("username")//
                .passwordParameter("password").and().rememberMe().rememberMeParameter("remember-me")
                .tokenRepository(tokenRepository).tokenValiditySeconds(1 * 24 * 60 * 60)
                // Cấu hình cho Logout Page.
                .and().logout().logoutUrl("/user/logout")
                .and().exceptionHandling().accessDeniedPage("/user/Access_Denied");
    }

}
