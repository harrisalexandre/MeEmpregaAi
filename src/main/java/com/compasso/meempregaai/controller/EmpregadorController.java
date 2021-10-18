package com.compasso.meempregaai.controller;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.EmpregadorForm;
import com.compasso.meempregaai.modelo.*;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
import com.compasso.meempregaai.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.*;


@RestController
@RequestMapping("/empregador")
public class EmpregadorController {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private VagaRepository vagaRepository;

    @PostMapping
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<EmpregadorDto> cadastrarEmpregador(@RequestBody @Valid EmpregadorForm empregadorForm, UriComponentsBuilder uriBuilder) {

        Empregador empregador = empregadorForm.converter(empregadorForm);
        Optional<Perfil> optionalPerfil = Optional.ofNullable(perfilRepository.findById(2l));

        if(optionalPerfil.isPresent()){
            Set<Perfil> perfis = new HashSet<>();
            perfis.add(optionalPerfil.get());
            empregador.setPerfis(perfis);
            empregadorRepository.save(empregador);
            URI uri = uriBuilder.path("/empregador/{id}").buildAndExpand(empregador.getId()).toUri();

            return ResponseEntity.created(uri).body(new EmpregadorDto(empregador));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "listaEmpregador")
    public ResponseEntity<?> listaEmpregador (Pageable pageable){
        List<Empregador> empregadores = empregadorRepository.findAllByAtivoIsTrue(pageable);
        return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
    }

    @GetMapping("/{id}")
    @Cacheable(value = "listaEmpregador")
    public ResponseEntity<?> detalhaEmpregador (@PathVariable Long id){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()){
            Empregador empregador = optionalEmpregador.get();

            return ResponseEntity.ok(new EmpregadorDto(empregador));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<?> atualizarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid EmpregadorForm form){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                empregador = form.atualizar(empregador.getId(), empregadorRepository);
                return ResponseEntity.ok(new EmpregadorDto(empregador));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<?> inativarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                List<Vaga> vagas = vagaRepository.findByEmpregador(empregador);
                for(Vaga vaga : vagas){
                    vaga.setAtiva(false);
                }

                empregador.setAtivo(false);
                return ResponseEntity.ok(new EmpregadorDto(empregador));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<?> reativarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                empregador.setAtivo(true);
                return ResponseEntity.ok(new EmpregadorDto(empregador));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
