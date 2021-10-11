package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.AdminDto;
import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.form.AdminForm;
import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Perfil;
import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<AdminDto> cadastrarAdmin(@RequestBody @Valid AdminForm adminForm, UriComponentsBuilder uriBuilder) {

        Admin admin = adminForm.converter(adminForm, adminRepository);
        Optional<Perfil> optionalPerfil= Optional.ofNullable(perfilRepository.findById(2l));
        if (optionalPerfil.isPresent()){
            Set<Perfil> perfis = new HashSet<>();
            admin.setPerfis(perfis);
            adminRepository.save(admin);
            URI uri = uriBuilder.path("/admin/{id}").buildAndExpand(admin.getId()).toUri();

            return ResponseEntity.created(uri).body(new AdminDto(admin));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/admin")
    @Transactional
    public ResponseEntity<?> atualizarAdmin (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid AdminForm form){
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findById(id));

        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            if (logado.getId().equals(admin.getId())&& logado.getTipo().equals(admin.getTipo())){
               Admin admin1 = form.atualizar(AdminDto.getAdmin().getId(),adminRepository);
                return ResponseEntity.ok(new AdminDto(admin1));
            }
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return  ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> inativarAdmin (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findById(id));

        if(optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            if(logado.getId().equals(admin.getId()) && logado.getTipo().equals(admin.getTipo())){
                admin.setAtivo(false);
                return ResponseEntity.ok(new AdminDto(admin));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
