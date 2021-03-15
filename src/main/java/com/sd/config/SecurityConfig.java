package com.sd.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import static com.sd.config.EnumUserPermission.*;
import static com.sd.config.EnumUserRole.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*")
                .permitAll()
                .antMatchers("/api/**")
                .hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE,"/admin/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/admin/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT,"/admin/api/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers("/admin/api/**").hasAnyRole(ADMIN.name(), ADMIN_READ.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        UserDetails adminUser = User.builder()
                .username("admin")
                .password(passwordEncoder.encode("12345"))
//                .roles(ADMIN.name())
                .authorities(ADMIN.getAuthorities())
                .build();

        UserDetails adminRead = User.builder()
                .username("admin1")
                .password(passwordEncoder.encode("12345"))
//                .roles(ADMIN_READ.name())
                .authorities(ADMIN_READ.getAuthorities())
                .build();


        UserDetails studentUser = User.builder()
                .username("student")
                .password(passwordEncoder.encode("12345"))
//                .roles(STUDENT.name())
                .authorities(STUDENT.getAuthorities())
                .build();


        return new InMemoryUserDetailsManager(
                adminUser,
                adminRead,
                studentUser);
    }
}
