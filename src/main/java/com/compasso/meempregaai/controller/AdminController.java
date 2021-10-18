package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.AdminDto;
import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.AdminForm;
import com.compasso.meempregaai.controller.form.BuscaAdminForm;
import com.compasso.meempregaai.controller.form.BuscaEmpregadoForm;
import com.compasso.meempregaai.controller.form.BuscaEmpregadorForm;
import com.compasso.meempregaai.modelo.*;
import com.compasso.meempregaai.repository.AdminRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @PostMapping
    @CacheEvict(value = "listarAdmin",allEntries = true)
    public ResponseEntity<AdminDto> cadastrarAdmin(@RequestBody @Valid AdminForm adminForm, UriComponentsBuilder uriBuilder) {

        Admin admin = adminForm.converter(adminForm);
        Optional<Perfil> op1= Optional.ofNullable(perfilRepository.findById(3l));
        Optional<Perfil> op2 = Optional.ofNullable(perfilRepository.findById(2l));
        Optional<Perfil> op3 = Optional.ofNullable(perfilRepository.findById(1l));

        if (op1.isPresent() && op2.isPresent() && op3.isPresent()){
            Set<Perfil> perfis = new HashSet<>();
            perfis.add(op1.get());
            perfis.add(op2.get());
            perfis.add(op3.get());
            admin.setPerfis(perfis);
            adminRepository.save(admin);
            URI uri = uriBuilder.path("/admin/{id}").buildAndExpand(admin.getId()).toUri();

            return ResponseEntity.created(uri).body(new AdminDto(admin));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "listarAdmin")
    public ResponseEntity<?> listaAdmin (BuscaAdminForm form, Pageable pageable){

        List<Admin> admins = adminRepository.findAll(form.toSpec(), pageable).getContent();

        return ResponseEntity.ok(AdminDto.converter(admins));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "listarAdmin")
    public ResponseEntity<?> detalhaAdmin (@PathVariable Long id){

        Optional<Admin> optionalAdmin= Optional.ofNullable(adminRepository.findById(id));

        if(optionalAdmin.isPresent()) {
            return ResponseEntity.ok(new AdminDto(optionalAdmin.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @CacheEvict(value = "listarAdmin",allEntries = true)
    @Transactional
    public ResponseEntity<?> atualizarAdmin (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid AdminForm form){
        Optional<Admin> optionalAdmin = Optional.ofNullable(adminRepository.findById(id));

        if (optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            if (logado.getId().equals(admin.getId()) && logado.getTipo().equals(admin.getTipo())){

               admin = form.atualizar(id, adminRepository);
               return ResponseEntity.ok(new AdminDto(admin));
            }
            return  ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return  ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @CacheEvict(value = "listarAdmin",allEntries = true)
    @Transactional
    public ResponseEntity<?> inativarAdmin (@PathVariable Long id){

        Optional<Admin> optionalAdmin= Optional.ofNullable(adminRepository.findById(id));

        if(optionalAdmin.isPresent()) {
            Admin admin = optionalAdmin.get();
            admin.setAtivo(false);
            return ResponseEntity.ok(new AdminDto(admin));
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/empregado")
    @Cacheable(value = "listarUmEmpregado")
    public ResponseEntity<?> listaTodosEmpregados (BuscaEmpregadoForm form, Pageable pageable){

        List<Empregado> empregados = empregadoRepository.findAll(form.toSpec(), pageable).getContent();
        return ResponseEntity.ok(EmpregadoDto.converter(empregados));
    }

    @GetMapping("/empregador")
    @Cacheable(value = "listaEmpregador")
    public ResponseEntity<?> listaTodosEmpregadores (BuscaEmpregadorForm form, Pageable pageable){

        List<Empregador> empregadores = empregadorRepository.findAll(form.toSpec(), pageable).getContent();
        return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> reativarAdmin (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){
        Optional <Admin> optionalAdmin = Optional.ofNullable(adminRepository.findById(id));

        if (optionalAdmin.isPresent() && logado.isAtivo()){
            Admin admin = optionalAdmin.get();
            Admin admin1 = admin.getAdmin();
            if (logado.getId().equals(admin1.getId()) && logado.getTipo().equals(admin1.getTipo())|| logado.getTipo().equals(Admin.class.getSimpleName())){
                admin.setAtivo(true);
                return ResponseEntity.ok(new AdminDto(optionalAdmin.get()));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
