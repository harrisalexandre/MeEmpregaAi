package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.CurriculoDto;
import com.compasso.meempregaai.controller.form.CurriculoForm;
import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.repository.CurriculoRepository;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/curriculo")
public class CurriculoController {

    @Autowired
    private CurriculoRepository curriculoRepository;

    @PostMapping
    public ResponseEntity<Curriculo> cadastraCurriculo(@RequestBody @Valid CurriculoForm curriculoForm, UriComponentsBuilder uriComponentsBuilder){
        Curriculo curriculo = curriculoForm.converter(curriculoForm);
        curriculoRepository.save(curriculo);

        URI uri = uriComponentsBuilder.path("/curriculo/{id}").buildAndExpand(curriculo.getId()).toUri();

        return  ResponseEntity.created(uri).body(curriculo);
    }

    @GetMapping("/{id}")
    public  ResponseEntity<?> detalhaCurriculo (@PathVariable Long id){
        Optional<Curriculo> optionalCurriculo = Optional.ofNullable(curriculoRepository.findById(id));
        if (optionalCurriculo.isPresent()){
            return ResponseEntity.ok(new CurriculoDto(optionalCurriculo.get()));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removerCurriculoPorId(@PathVariable Long id){
        Optional<Curriculo> optional = Optional.ofNullable(curriculoRepository.findById(id));
        if (optional.isPresent()) {
            curriculoRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    @Transactional
    public ResponseEntity<Curriculo> atualizaCurriculoPorId(@PathVariable Long id, @RequestBody @Valid CurriculoForm form){

        Optional<Curriculo> curriculo = Optional.ofNullable(curriculoRepository.findById(id));

        if (curriculo.isPresent()){
           form.atualizar(curriculo.get());
           return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
