package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.BuscaEmpregadorForm;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/Empregador")
public class EmpregadorController {

    @Autowired
    private EmpregadorRepository empregadorRepository;

    @GetMapping
    public ResponseEntity<?> listaEmpregador (BuscaEmpregadorForm form, Pageable pageable){

        List<Empregador> empregadores = empregadorRepository.findAll(form.toSpec(), pageable).getContent();
        if(empregadores.size()> 0) {
            return ResponseEntity.ok(EmpregadorDto.converter(empregadores));
        }
        return ResponseEntity.notFound().build();
    }
}
