package com.atcons.bclient.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Created by Alexey on 16.08.2017 15:56.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final
    DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Autowired

    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        System.out.println("configAuthentication");
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
      /*  auth.jdbcAuthentication().dataSource(dataSource)

                .usersByUsernameQuery(

                        "select user_login, user_password, enabled from bclient.dim_user where user_login=?")

                .authoritiesByUsernameQuery(

                        "select username, role from user_roles where username=?");*/

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login-process")
                .permitAll()
                .and()
                .logout()
                .permitAll();
    }

}
