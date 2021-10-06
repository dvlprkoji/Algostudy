package com.example.algostudy.config;

import com.example.algostudy.security.authentication.provider.FormAuthenticationProvider;
import com.example.algostudy.security.authorize.filter.PermitAllFilter;
import com.example.algostudy.security.authorize.metadata.UrlFilterInvocationSecurityMetadataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String[] ignoredMatcherPattern = {"/images/**","/vendor/**","/fonts/**","/img/**","/scss/**","/static/**", "/css/**", "/js/**", "/static/css/images/**", "/webjars/**", "/**/favicon.ico"};
    private String[] permitAllPattern = {"/","/login","/register"};

    @Autowired
    private FormAuthenticationProvider formAuthenticationProvider;

//    @Autowired
//    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
//
//    @Autowired
//    private AccessDecisionManager accessDecisionManager;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(ignoredMatcherPattern);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(formAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable()
                // 모든 요청에 인증 요구
                .authorizeRequests()
                .antMatchers("/", "/login", "/logout").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/")
                .usernameParameter("email")
                .passwordParameter("password")
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/");
    }
//
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

//    @Bean
//    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
//        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
//        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
//        return filterSecurityInterceptor;
//    }
//
//    @Bean
//    public AccessDecisionManager affirmativeBased() {
//        AffirmativeBased affirmativeBased = new AffirmativeBased(getAccessDecisionVoters());
//        return affirmativeBased;
//    }
//
//    private List<AccessDecisionVoter<?>> getAccessDecisionVoters() {
//        return Arrays.asList(new RoleVoter());
//    }
//
//
//    @Bean
//    public UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource() {
//        return new UrlFilterInvocationSecurityMetadataSource();
//    }
//
//    @Bean
//    public PermitAllFilter permitAllFilter() {
//
//        PermitAllFilter permitAllFilter = new PermitAllFilter(permitAllPattern);
//        permitAllFilter.setAuthenticationManager(authenticationManager());
//        permitAllFilter.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource());
//        permitAllFilter.setAccessDecisionManager(affirmativeBased());
//        permitAllFilter.setRejectPublicInvocations(false);
//        return permitAllFilter;
//    }
//
    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
//
//
//
//
//    public AuthenticationManager authenticationManager() {
//        List<AuthenticationProvider> authenticationProviderList = new ArrayList<>();
//        authenticationProviderList.add(formAuthenticationProvider);
//        ProviderManager providerManager = new ProviderManager(authenticationProviderList);
//        return providerManager;
//    }
}
