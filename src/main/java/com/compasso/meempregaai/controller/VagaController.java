package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.VagaDto;
import com.compasso.meempregaai.controller.form.BuscaVagaForm;
import com.compasso.meempregaai.controller.form.VagaForm;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.modelo.Vaga;
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
    public ResponseEntity<VagaDto> cadastrarVaga(@RequestBody @Valid VagaForm vagaForm, UriComponentsBuilder uriBuilder) {

        Vaga vaga = vagaForm.converter(vagaForm, empregadorRepository);
        vagaRepository.save(vaga);

        URI uri = uriBuilder.path("/vaga/{id}").buildAndExpand(vaga.getId()).toUri();

        return ResponseEntity.created(uri).body(new VagaDto(vaga));
    }

    @PostMapping("/{idEmpregado}/candidatar/{idVaga}")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<VagaDto> condidatarVaga(@PathVariable Long idEmpregado, @PathVariable Long idVaga) {
        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(idVaga));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(idEmpregado));

        if(optionalVaga.isPresent() && optionalEmpregado.isPresent()){
            Vaga vaga = optionalVaga.get();
            Empregado empregado = optionalEmpregado.get();
            vaga.getEmpregados().add(empregado);
            vagaRepository.save(vaga);

            return ResponseEntity.ok(new VagaDto(vaga));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/curtir")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<VagaDto> curtirVaga (@PathVariable Long id) {
        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()){
            Vaga vaga = optionalVaga.get();
            vaga.setCurtidas(vaga.getCurtidas()+ 1);
            vagaRepository.save(vaga);
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
    public ResponseEntity<?> listaVaga (BuscaVagaForm form, Pageable pageable){

        List<Vaga> vagas = vagaRepository.findAll(form.toSpec(), pageable).getContent();
        if(vagas.size()> 0) {
            return ResponseEntity.ok(VagaDto.converter(vagas));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "buscarListaVaga", allEntries = true)
    public ResponseEntity<?> inativaVaga (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Vaga> optionalVaga = Optional.ofNullable(vagaRepository.findById(id));

        if(optionalVaga.isPresent()) {
            Vaga vaga = optionalVaga.get();
            Empregador empregador = vaga.getEmpregador();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                vaga.setAtiva(false);
                vagaRepository.save(vaga);
                return ResponseEntity.ok(new VagaDto(optionalVaga.get()));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

}