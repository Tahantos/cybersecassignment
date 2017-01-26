package sec.project.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // no real security at the moment
        http.authorizeRequests().anyRequest().permitAll();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
        http.sessionManagement().invalidSessionUrl("/login.html");
        http.sessionManagement().sessionFixation().migrateSession();
        http.csrf().disable();
        
    }

}
