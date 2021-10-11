package com.compasso.meempregaai.config.security;

import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
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

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findByEmail(userName));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findByEmail(userName));
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findByEmail(userName));

        if(optionalEmpregado.isPresent()) {
            return optionalEmpregado.get();
        }else if(optionalEmpregador.isPresent()){
            return optionalEmpregador.get();
        }else if(optionalAdmin.isPresent()) {
            return optionalAdmin.get();
        }
        throw new UsernameNotFoundException("Dados inválidos!");
    }
}
