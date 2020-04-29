package kovteba.onlineshopapi.config;

import kovteba.onlineshopapi.filter.JwtRequestFilter;
import kovteba.onlineshopapi.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Autowired
    private JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public UserDetailsService getUserDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()

                .authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/resetPass").permitAll()
                .antMatchers("/createSecretToken").permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("http://localhost:8080").permitAll()

                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/v2/api-docs",           // swagger
                        "/webjars/**",            // swagger-ui webjars
                        "/swagger-resources/**",  // swagger-ui resources
                        "/configuration/**",      // swagger configuration
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js"
                ).permitAll()

                .antMatchers("/product/add").hasRole("ADMIN")
                .antMatchers("/product/**").hasRole("ADMIN")
                .antMatchers("/product/info/*").hasRole("ADMIN")
                .antMatchers("/product/image/*").hasRole("ADMIN")
                .antMatchers("/product/file/*").hasRole("ADMIN")
                .antMatchers("/product/").hasRole("ADMIN")

                .antMatchers("/user/role/*").hasRole("ADMIN")
                .antMatchers("/user/phone/*").hasRole("ADMIN")
                .antMatchers("/user/ban/*").hasRole("ADMIN")
                .antMatchers("/user/unBan/*").hasRole("ADMIN")
                .antMatchers("/user/email/*").hasRole("ADMIN")

                .antMatchers("/product/*").hasRole("USER")
                .antMatchers("/user/basket/*").hasRole("USER")
                .antMatchers("/user/genRec/*").hasRole("USER")

                .anyRequest()
                .authenticated();

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

}

