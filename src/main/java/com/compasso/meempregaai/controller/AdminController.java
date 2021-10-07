package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.AdminDto;
import com.compasso.meempregaai.controller.form.AdminForm;
import com.compasso.meempregaai.modelo.Admin;
import com.compasso.meempregaai.modelo.Perfil;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            List<Perfil> admins = new ArrayList<>();
            admin.setPerfis(admins);
            adminRepository.save(admin);
            URI uri = uriBuilder.path("/admin/{id}").buildAndExpand(admin.getId()).toUri();

            return ResponseEntity.created(uri).body(new AdminDto(admin));
        }
        return ResponseEntity.notFound().build();
    }
}
