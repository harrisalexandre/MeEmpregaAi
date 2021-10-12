package com.compasso.meempregaai.config.security;

import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AutenticacaoViaTokenFilter extends OncePerRequestFilter {

    private TokenConfigurations tokenConfigurations;
    private EmpregadoRepository empregadoRepository;
    private EmpregadorRepository empregadorRepository;
    private AdminRepository adminRepository;

    public AutenticacaoViaTokenFilter(TokenConfigurations tokenConfigurations, EmpregadoRepository empregadoRepository, EmpregadorRepository empregadorRepository, AdminRepository adminRepository) {
        this.tokenConfigurations = tokenConfigurations;
        this.empregadoRepository = empregadoRepository;
        this.empregadorRepository = empregadorRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = recuperarToken(request);
        boolean valido = tokenConfigurations.isTokenValido(token);
        if (valido){
            autenticarUsuario(token);
        }
        filterChain.doFilter(request, response);
    }

    private void autenticarUsuario(String token) {
        String[] dados = tokenConfigurations.getUsuarioIdTipo(token);
        Usuario usuario;

        switch (dados[0]){
            case "EO":
                usuario = empregadoRepository.findById(Long.parseLong(dados[1]));
                break;
            case "ER":
                usuario = empregadorRepository.findById(Long.parseLong(dados[1]));
                break;
            case "ADM":
                usuario = adminRepository.findById(Long.parseLong(dados[1]));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + dados[0]);
        }
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getPerfis());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private String recuperarToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if(token == null || token.isEmpty() || !token.startsWith("Bearer ")){
            return null;
        }
        return token.substring(7, token.length());
    }
}
