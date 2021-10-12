package com.compasso.meempregaai.controller;
import com.compasso.meempregaai.controller.dto.CurriculoDto;
import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.AtualizaCurriculoForm;
import com.compasso.meempregaai.controller.form.BuscaEmpregadorForm;
import com.compasso.meempregaai.controller.form.EmpregadorForm;
import com.compasso.meempregaai.modelo.*;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/empregador")
public class EmpregadorController {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<EmpregadorDto> cadastrarEmpregador(@RequestBody @Valid EmpregadorForm empregadorForm, UriComponentsBuilder uriBuilder) {

        Empregador empregador = empregadorForm.converter(empregadorForm);
        Optional<Perfil> optionalPerfil = Optional.ofNullable(perfilRepository.findById(2l));

        if(optionalPerfil.isPresent()){
            List<Perfil> perfils = new ArrayList<>();
            perfils.add(optionalPerfil.get());
            empregador.setPerfis(perfils);
            empregadorRepository.save(empregador);
            URI uri = uriBuilder.path("/empregador/{id}").buildAndExpand(empregador.getId()).toUri();

            return ResponseEntity.created(uri).body(new EmpregadorDto(empregador));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{idEmpregador}/contratar/{idEmpregado}")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<EmpregadorDto> contratarEmpregado (@PathVariable Long idEmpregador, @AuthenticationPrincipal Usuario logado, @PathVariable Long idEmpregado) {
        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(idEmpregador));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(idEmpregado));

        if(optionalEmpregador.isPresent() && optionalEmpregado.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                Empregado empregado = optionalEmpregado.get();
                empregador.getEmpregados().add(empregado);
                empregadorRepository.save(empregador);

                return ResponseEntity.ok(new EmpregadorDto(empregador));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "listaEmpregador")
    public ResponseEntity<?> listaEmpregador (BuscaEmpregadorForm form, Pageable pageable){

        List<Empregador> empregadores = empregadorRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregadores.size()> 0) {
            return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/empregados")
    @Cacheable(value = "listaEmpregadosDoEmpregador")
    public ResponseEntity<?> listaEmpregadosDoEmpregador (@PathVariable Long id){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            List<Empregado> empregados = empregador.getEmpregados();

            return ResponseEntity.ok(EmpregadoDto.converter(empregados));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "listaEmpregadosDoEmpregadorPorId")
    public ResponseEntity<?> detalhaEmpregador (@PathVariable Long id){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()){
            Empregador empregador = optionalEmpregador.get();

            return ResponseEntity.ok(new EmpregadorDto(empregador));
        }
        return ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}/empregador")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<?> atualizarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid EmpregadorForm form){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                Empregador empregador1 = form.atualizar(empregador.getEmpregador().getId(), empregadorRepository);
                return ResponseEntity.ok(new EmpregadorDto(empregador1));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> inativarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                empregador.setAtivo(false);
                return ResponseEntity.ok(new EmpregadorDto(empregador));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> reativarEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                empregador.setAtivo(true);
                return ResponseEntity.ok(new EmpregadorDto(empregador));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

}
