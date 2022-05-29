package com.jojoldu.book.springboot.config;

import com.jojoldu.book.springboot.config.auth.PrincipalDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@Slf4j
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    // 암호화 방식 빈(Bean) 생성
    @Autowired
    private PrincipalDetailsService principalDetailsService;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception
    {
        // static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**");

    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().headers().frameOptions().disable()
                .and()
                .authorizeRequests() // user 페이지 설정
         .antMatchers("/user/**").authenticated() // 로그인 필요 // 기타 url은 모두 허용
         .anyRequest()
                .permitAll()
                .and() // 로그인 페이지 사용
         .formLogin()
                .loginPage("/loginForm")// 로그인 페이지 경로 설정
                .defaultSuccessUrl("/")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                 // 로그인이 실제 이루어지는 곳

                .permitAll() // 로그인 성공 후 기본적으로 리다이렉트되는 경로
        .and() // 로그아웃 설정
                .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true);

    }
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(principalDetailsService).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        return new CustomAuthenticationProvider();
    }

}


