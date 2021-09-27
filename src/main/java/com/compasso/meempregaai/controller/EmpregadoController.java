package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.VagaDto;
import com.compasso.meempregaai.controller.form.EmpregadoForm;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/empregado")
public class EmpregadoController {

    @Autowired
    private EmpregadoRepository empregadoRepository;

    @PostMapping
    public ResponseEntity<Empregado> cadastrarEmpregado(@RequestBody @Valid EmpregadoForm empregadoForm, UriComponentsBuilder uriBuilder) {

        Empregado empregado = empregadoForm.converter(empregadoForm);
        empregadoRepository.save(empregado);

        URI uri = uriBuilder.path("/empregado/{id}").buildAndExpand(empregado.getId()).toUri();

        return ResponseEntity.created(uri).body(empregado);
    }
}
