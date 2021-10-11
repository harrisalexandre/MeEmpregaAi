package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.CurriculoDto;
import com.compasso.meempregaai.controller.form.AtualizaCurriculoForm;
import com.compasso.meempregaai.modelo.Curriculo;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.repository.CurriculoRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/empregado")
public class CurriculoController {

    @Autowired
    EmpregadoRepository empregadoRepository;

    @Autowired
    CurriculoRepository curriculoRepository;

    @GetMapping("/{id}/curriculo")
    @Cacheable(value = "buscarCurriculoPorId")
    public ResponseEntity<?> detalhaCurriculo (@PathVariable Long id){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Curriculo curriculo = optionalEmpregado.get().getCurriculo();
            return ResponseEntity.ok(new CurriculoDto(curriculo));
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/curriculo")
    @Transactional
    @CacheEvict(value = "buscarCurriculoPorId",allEntries = true)
    public ResponseEntity<?> atualizaCurriculo (@PathVariable Long id, @AuthenticationPrincipal Usuario logado, @RequestBody @Valid AtualizaCurriculoForm form){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                Curriculo curriculo = form.atualizar(empregado.getCurriculo().getId(), curriculoRepository);
                return ResponseEntity.ok(new CurriculoDto(curriculo));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}/curriculo")
    @Transactional
    @CacheEvict(value = "buscarCurriculoPorId",allEntries = true)
    public ResponseEntity<?> resetarCurriculo (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                Curriculo curriculo = new AtualizaCurriculoForm().resetar(empregado.getCurriculo().getId(), curriculoRepository);
                return ResponseEntity.ok(new CurriculoDto(curriculo));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
