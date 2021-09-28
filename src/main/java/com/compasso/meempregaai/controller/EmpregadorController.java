package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.EmpregadoDto;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.BuscaEmpregadorForm;
import com.compasso.meempregaai.controller.form.EmpregadorForm;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/empregador")
public class EmpregadorController {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @Autowired
    private EmpregadoRepository empregadoRepository;


    @PostMapping
    public ResponseEntity<Empregador> cadastrarEmpregador(@RequestBody @Valid EmpregadorForm empregadorForm, UriComponentsBuilder uriBuilder) {

        Empregador empregador = empregadorForm.converter(empregadorForm);
        empregadorRepository.save(empregador);

        URI uri = uriBuilder.path("/empregado/{id}").buildAndExpand(empregador.getId()).toUri();

        return ResponseEntity.created(uri).body(empregador);
    }

    @PostMapping("/{idEmpregador}/contratar/{idEmpregado}")
    @Transactional
    public ResponseEntity<EmpregadorDto> contratarEmpregado (@PathVariable Long idEmpregador, @PathVariable Long idEmpregado) {
        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(idEmpregador));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(idEmpregado));

        if(optionalEmpregador.isPresent() && optionalEmpregado.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            Empregado empregado = optionalEmpregado.get();
            empregador.getEmpregados().add(empregado);
            empregadorRepository.save(empregador);

            return ResponseEntity.ok(new EmpregadorDto(empregador));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> listaEmpregador (BuscaEmpregadorForm form, Pageable pageable){

        List<Empregador> empregadores = empregadorRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregadores.size()> 0) {
            return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/empregados")
    public ResponseEntity<?> listaEmpregadosDoEmpregador (@PathVariable Long id){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            List<Empregado> empregados = empregador.getEmpregados();

            return ResponseEntity.ok(EmpregadoDto.converter(empregados));
        }
        return ResponseEntity.notFound().build();
    }


}
