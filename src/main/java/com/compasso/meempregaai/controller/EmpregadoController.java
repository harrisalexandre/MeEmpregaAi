package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.CurriculoDto;
import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.AtualizaCurriculoForm;
import com.compasso.meempregaai.controller.form.AtualizarEmpregado;
import com.compasso.meempregaai.controller.form.BuscaEmpregadoForm;
import com.compasso.meempregaai.controller.form.EmpregadoForm;
import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Perfil;
import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.repository.CurriculoRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
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
import java.util.*;

@RestController
@RequestMapping("/empregado")
public class EmpregadoController {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @Autowired
    private CurriculoRepository curriculoRepository;

    @PostMapping
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<EmpregadoDto> cadastrarEmpregado(@RequestBody @Valid EmpregadoForm empregadoForm, UriComponentsBuilder uriBuilder) {

        Empregado empregado = empregadoForm.converter(empregadoForm);
        Optional<Perfil> optionalPerfil = Optional.ofNullable(perfilRepository.findById(1l));

        if(optionalPerfil.isPresent()){
            Set<Perfil> perfis = new HashSet<>();
            perfis.add(optionalPerfil.get());
            empregado.setPerfis(perfis);
            Curriculo curriculo = new Curriculo(empregado);
            curriculoRepository.save(curriculo);
            empregado.setCurriculo(curriculo);
            empregadoRepository.save(empregado);
            URI uri = uriBuilder.path("/empregado/{id}").buildAndExpand(empregado.getId()).toUri();

            return ResponseEntity.created(uri).body(new EmpregadoDto(empregado));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/curtir")
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<EmpregadoDto> curtirEmpregado (@PathVariable Long id) {
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()){
            Empregado empregado = optionalEmpregado.get();
            empregado.setCurtidas(empregado.getCurtidas()+ 1);
            empregadoRepository.save(empregado);
            return ResponseEntity.ok(new EmpregadoDto(empregado));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "buscarUmEmpregado")
    public ResponseEntity<?> detalhaEmpregado (@PathVariable Long id){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            return ResponseEntity.ok(new EmpregadoDto(optionalEmpregado.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    @Cacheable(value = "listarUmEmpregado")
    public ResponseEntity<?> listaEmpregado (BuscaEmpregadoForm form, Pageable pageable){

        List<Empregado> empregados = empregadoRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregados.size()> 0) {
            return ResponseEntity.ok(EmpregadoDto.converter(empregados));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> inativarEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                empregado.setAtivo(false);
                return ResponseEntity.ok(new EmpregadoDto(empregado));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
    public ResponseEntity<?> reativarEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                empregado.setAtivo(true);
                return ResponseEntity.ok(new EmpregadoDto(empregado));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> atualizaEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid AtualizarEmpregado form){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                empregado = form.atualizar(id, empregadoRepository);
                return ResponseEntity.ok(new EmpregadoDto(empregado));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}