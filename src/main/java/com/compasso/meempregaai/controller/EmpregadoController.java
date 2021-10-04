package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.form.BuscaEmpregadoForm;
import com.compasso.meempregaai.controller.form.EmpregadoForm;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Perfil;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.PerfilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/empregado")
public class EmpregadoController {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    @PostMapping
    public ResponseEntity<EmpregadoDto> cadastrarEmpregado(@RequestBody @Valid EmpregadoForm empregadoForm, UriComponentsBuilder uriBuilder) {

        Empregado empregado = empregadoForm.converter(empregadoForm);
        Optional<Perfil> optionalPerfil = Optional.ofNullable(perfilRepository.findById(1l));

        if(optionalPerfil.isPresent()){
            List<Perfil> perfils = new ArrayList<>();
            perfils.add(optionalPerfil.get());
            empregado.setPerfis(perfils);
            empregadoRepository.save(empregado);
            URI uri = uriBuilder.path("/empregado/{id}").buildAndExpand(empregado.getId()).toUri();

            return ResponseEntity.created(uri).body(new EmpregadoDto(empregado));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/{id}/curtir")
    @Transactional
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
    public ResponseEntity<?> detalhaEmpregado (@PathVariable Long id){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            return ResponseEntity.ok(new EmpregadoDto(optionalEmpregado.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> listaEmpregado (BuscaEmpregadoForm form, Pageable pageable){

        List<Empregado> empregados = empregadoRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregados.size()> 0) {
            return ResponseEntity.ok(EmpregadoDto.converter(empregados));
        }
        return ResponseEntity.notFound().build();
    }
}
