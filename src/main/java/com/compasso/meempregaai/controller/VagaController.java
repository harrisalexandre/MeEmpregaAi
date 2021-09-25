package com.compasso.meempregaai.controller;


import com.compasso.meempregaai.controller.dto.VagaDto;
import com.compasso.meempregaai.controller.form.VagaForm;
import com.compasso.meempregaai.modelo.Vaga;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import com.compasso.meempregaai.repository.VagaRepository;
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
@RequestMapping("/vaga")
public class VagaController {

    @Autowired
    private VagaRepository vagaRepository;
    @Autowired
    private EmpregadorRepository empregadorRepository;

    @PostMapping
    public ResponseEntity<VagaDto> cadastrarVaga(@RequestBody @Valid VagaForm vagaForm, UriComponentsBuilder uriBuilder) {

        Vaga vaga = vagaForm.converter(vagaForm, empregadorRepository);
        vagaRepository.save(vaga);

        URI uri = uriBuilder.path("/api/partidos/{id}").buildAndExpand(vaga.getId()).toUri();

        return ResponseEntity.created(uri).body(new VagaDto(vaga));
    }

}
