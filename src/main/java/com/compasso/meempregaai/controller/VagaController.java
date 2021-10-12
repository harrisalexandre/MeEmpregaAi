package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.VagaDto;
import com.compasso.meempregaai.controller.form.BuscaVagaForm;
import com.compasso.meempregaai.controller.form.VagaForm;
import com.compasso.meempregaai.modelo.*;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import com.compasso.meempregaai.repository.VagaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;
    @Autowired
    private EmpregadorRepository empregadorRepository;
    @Autowired
    private EmpregadoRepository empregadoRepository;

    @PostMapping
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<VagaDto> cadastrarVaga(@RequestBody @Valid VagaForm vagaForm,@AuthenticationPrincipal Usuario logado, UriComponentsBuilder uriBuilder) {

        if(logado.getId().equals(vagaForm.getEmpregadorId()) && logado.getTipo().equals(Empregador.class.getSimpleName()) || logado.getTipo().equals(Admin.class.getSimpleName())){
            Vaga vaga = vagaForm.converter(vagaForm, empregadorRepository);
            vagaRepository.save(vaga);

            URI uri = uriBuilder.path("/vaga/{id}").buildAndExpand(vaga.getId()).toUri();

            return ResponseEntity.created(uri).body(new VagaDto(vaga));
        }

        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @PostMapping("/{idEmpregado}/candidatar/{idVaga}")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<VagaDto> candidatarVaga(@PathVariable Long idEmpregado, @PathVariable Long idVaga, @AuthenticationPrincipal Usuario logado) {
        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(idVaga));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(idEmpregado));

        if(optionalVaga.isPresent() && optionalEmpregado.isPresent()){
            Vaga vaga = optionalVaga.get();
            Empregado empregado = optionalEmpregado.get();
            if(vaga.isAtiva() && logado.getId().equals(empregado.getId()) && logado.getTipo().equals(Empregado.class.getSimpleName()) || vaga.isAtiva() && logado.getTipo().equals(Admin.class.getSimpleName())){
               List<Empregado> candidatos = vaga.getCandidatos();
               if(candidatos.contains(empregado)){
                   candidatos.remove(empregado);
               }else{
                   candidatos.add(empregado);
               }
                vaga.setCandidatos(candidatos);
                return ResponseEntity.ok(new VagaDto(vaga));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/curtir")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<VagaDto> curtirVaga (@PathVariable Long id, @AuthenticationPrincipal Usuario logado) {
        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()){
            Empregado empregado = empregadoRepository.findById(logado.getId());
            Vaga vaga = optionalVaga.get();;
            List<Empregado> curtidas = vaga.getCurtidas();
            if(curtidas.contains(empregado)){
                 curtidas.remove(empregado);
            }else{
                curtidas.add(empregado);
            }
            vaga.setCurtidas(curtidas);
            return ResponseEntity.ok(new VagaDto(vaga));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "buscarVagaPorId")
    public ResponseEntity<?> detalhaVaga (@PathVariable Long id){

        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()) {
            return ResponseEntity.ok(new VagaDto(optionalVaga.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "buscarListaVaga")
    public ResponseEntity<?> listaVaga (@Valid BuscaVagaForm form, Pageable pageable){

        List<Vaga> vagas = vagaRepository.findAll(form.toSpec(), pageable).getContent();

        return ResponseEntity.ok(VagaDto.converter(vagas));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<?> inativaVaga (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()) {
            Vaga vaga = optionalVaga.get();
            Empregador empregador = vaga.getEmpregador();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                vaga.setAtiva(false);
                return ResponseEntity.ok(new VagaDto(optionalVaga.get()));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> reativarVaga (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()) {
            Vaga vaga = optionalVaga.get();
            Empregador empregador = vaga.getEmpregador();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                vaga.setAtiva(true);
                return ResponseEntity.ok(new VagaDto(optionalVaga.get()));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

}
