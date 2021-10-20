package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.form.AtualizarEmpregado;
import com.compasso.meempregaai.controller.form.BuscaEmpregadoForm;
import com.compasso.meempregaai.controller.form.EmpregadoForm;
import com.compasso.meempregaai.modelo.*;
import com.compasso.meempregaai.repository.*;
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

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private AdminRepository adminRepository;

    @PostMapping
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<EmpregadoDto> cadastrarEmpregado(@RequestBody @Valid EmpregadoForm empregadoForm, UriComponentsBuilder uriBuilder) {

        if(empregadoForm.emailNaoUsado(empregadoRepository, empregadorRepository, adminRepository)){
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
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("/{id}/curtir")
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<EmpregadoDto> curtirEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado) {
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));
        if(optionalEmpregado.isPresent()){
            Empregador empregador = empregadorRepository.findById(logado.getId());
            Empregado empregado = optionalEmpregado.get();

            List<Empregador> curtidas = empregado.getCurtidas();
            if(curtidas.contains(empregador)){
                curtidas.remove(empregador);
            }else{
                curtidas.add(empregador);
            }
            empregado.setCurtidas(curtidas);
            return ResponseEntity.ok(new EmpregadoDto(empregado));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @Cacheable(value = "listarUmEmpregado")
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

        List<Empregado> empregados = empregadoRepository.findAllByAtivoIsTrue(pageable);

        return ResponseEntity.ok(EmpregadoDto.converter(empregados));
    }

    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<?> inativarEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                empregado.setAtivo(false);
                return ResponseEntity.ok(new EmpregadoDto(empregado));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<?> reativarEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                empregado.setAtivo(true);
                return ResponseEntity.ok(new EmpregadoDto(empregado));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "listarUmEmpregado",allEntries = true)
    public ResponseEntity<?> atualizaEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid AtualizarEmpregado form){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo()) || logado.getTipo().equals(Admin.class.getSimpleName())){
                empregado = form.atualizar(id, empregadoRepository);
                return ResponseEntity.ok(new EmpregadoDto(empregado));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}