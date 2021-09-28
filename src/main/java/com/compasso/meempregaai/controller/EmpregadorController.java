package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.BuscaEmpregadorForm;
import com.compasso.meempregaai.controller.form.EmpregadorForm;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;



@RestController
@RequestMapping("/empregador")
public class EmpregadorController {

    @Autowired
    private EmpregadorRepository empregadorRepository;


    @PostMapping
    public ResponseEntity<Empregador> cadastrarEmpregador(@RequestBody @Valid EmpregadorForm empregadorForm, UriComponentsBuilder uriBuilder) {

        Empregador empregador = empregadorForm.converter(empregadorForm);
        empregadorRepository.save(empregador);

        URI uri = uriBuilder.path("/empregado/{id}").buildAndExpand(empregador.getId()).toUri();

        return ResponseEntity.created(uri).body(empregador);
    }


    @GetMapping
    public ResponseEntity<?> listaEmpregador (BuscaEmpregadorForm form, Pageable pageable){

        List<Empregador> empregadores = empregadorRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregadores.size()> 0) {
            return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
        }
        return ResponseEntity.notFound().build();
    }
}
