package com.example.algostudy.config;

import com.example.algostudy.security.authentication.handler.AuthenticationFailureHandler;
import com.example.algostudy.security.authentication.handler.AuthenticationSuccessHandler;
import com.example.algostudy.security.authentication.provider.FormAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private String[] ignoredMatcherPattern = {"/error", "/images/**","/vendor/**","/fonts/**","/img/**","/scss/**","/static/**", "/css/**", "/js/**", "/static/css/images/**", "/webjars/**", "/**/favicon.ico", "/templates/**"};
    private String[] permitAllPattern = {"/","/login","/register"};

    @Autowired
    private FormAuthenticationProvider formAuthenticationProvider;

    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;

//    @Autowired
//    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
//
//    @Autowired
//    private AccessDecisionManager accessDecisionManager;


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()).antMatchers(ignoredMatcherPattern);
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
                // ?????? ????????? ?????? ??????
                .authorizeRequests()
                .antMatchers("/", "/login**", "/logout", "/register").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login_proc")
                .defaultSuccessUrl("/")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
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
