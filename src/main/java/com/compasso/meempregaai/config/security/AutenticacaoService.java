package com.compasso.meempregaai.config.security;

import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findByEmail(userName));

        if(optionalEmpregado.isPresent()) {
            return optionalEmpregado.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}