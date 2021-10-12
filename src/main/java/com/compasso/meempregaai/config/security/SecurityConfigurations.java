package com.compasso.meempregaai.config.security;

import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@EnableWebSecurity
@Configuration
public class SecurityConfigurations extends WebSecurityConfigurerAdapter {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Autowired
    private TokenConfigurations tokenConfigurations;

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(autenticacaoService).passwordEncoder(new BCryptPasswordEncoder());
    }
    @Override    
    protected void configure(HttpSecurity http) throws Exception {
    	http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and().authorizeRequests().and()
        
      //  http.authorizeRequests()
        .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/auth").permitAll()

                .antMatchers(HttpMethod.GET, "/empregado").permitAll()
                .antMatchers(HttpMethod.GET, "/empregado/*").permitAll()
                .antMatchers(HttpMethod.POST, "/empregado").permitAll()
                .antMatchers(HttpMethod.GET, "/empregado/*/curriculo").permitAll()
                .antMatchers(HttpMethod.PUT, "/empregado/*/curriculo").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.DELETE, "/empregado/*/curriculo").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.DELETE, "/empregado/*").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.POST, "/empregado/*").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.PUT, "/empregado/*").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.POST, "/vaga/*candidatar/*").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.POST, "/vaga/*/curtir").hasRole("EMPREGADO")
                .antMatchers(HttpMethod.GET, "/vaga/*").permitAll()
                .antMatchers(HttpMethod.GET, "/vaga").permitAll()

                .antMatchers(HttpMethod.POST, "/empregado/*/curtir").hasRole("EMPREGADOR")
                .antMatchers(HttpMethod.GET, "/empregador").permitAll()
                .antMatchers(HttpMethod.GET, "/empregador/*").permitAll()
                .antMatchers(HttpMethod.GET, "/empregador/*/empregados").permitAll()
                .antMatchers(HttpMethod.POST, "/empregador").permitAll()
                .antMatchers(HttpMethod.POST, "/empregador/*/contratar/*").hasRole("EMPREGADOR")
                .antMatchers(HttpMethod.POST, "/vaga").hasRole("EMPREGADOR")
                .antMatchers(HttpMethod.DELETE, "/vaga/*").hasRole("EMPREGADOR")
                .antMatchers(HttpMethod.DELETE, "/empregador/*").hasRole("EMPREGADOR")
                .antMatchers(HttpMethod.POST, "/empregador/*").hasRole("EMPREGADOR")

                .antMatchers(HttpMethod.POST, "/empregado/*/curtir").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/vaga/*candidatar/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/vaga/*/curtir").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/empregador/*/contratar/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST, "/vaga").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/vaga/*").hasRole("ADMIN")

                .anyRequest().authenticated()
                .and().csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenConfigurations, empregadoRepository, empregadorRepository,adminRepository), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
}
