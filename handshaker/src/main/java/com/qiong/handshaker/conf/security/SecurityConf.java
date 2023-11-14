package com.qiong.handshaker.conf.security;

import com.qiong.handshaker.data.security.DataSecurityRouterConf;
import com.qiong.handshaker.worker.security.fiiter.SecurityAuthRequestFilter;
import com.qiong.handshaker.worker.security.handie.SecurityAuthFailureHandier;
import com.qiong.handshaker.worker.security.handie.SecurityForbiddenHandier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    SecurityAuthRequestFilter authRequestFilter;
    @Autowired
    SecurityForbiddenHandier forbiddenHandier;
    @Autowired
    SecurityAuthFailureHandier authFailureHandier;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        // http.headers().frameOptions().sameOrigin();

        http.authorizeRequests().antMatchers(DataSecurityRouterConf.WHITE_LIST).permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.exceptionHandling().authenticationEntryPoint(authFailureHandier).accessDeniedHandler(forbiddenHandier);

        // 请求的 TOKEN
        http.addFilterAt(authRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /*
    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    protected UserDetailsService detailsService() {
        return new AuthUserDetailService();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().and().csrf().disable();
        http.headers().frameOptions().sameOrigin();

        http.authorizeRequests().antMatchers(DataSecurityRouter.WHITE_LIST).permitAll();
        http.authorizeRequests().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
     */
}
