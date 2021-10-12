package com.compasso.meempregaai.controller;

import com.compasso.meempregaai.controller.dto.ContratoDto;
import com.compasso.meempregaai.controller.dto.EmpregadorDto;
import com.compasso.meempregaai.controller.form.AtualizarEmpregado;
import com.compasso.meempregaai.controller.form.ContratoForm;
import com.compasso.meempregaai.modelo.Contrato;
import com.compasso.meempregaai.modelo.Empregado;
import com.compasso.meempregaai.modelo.Empregador;
import com.compasso.meempregaai.modelo.Usuario;
import com.compasso.meempregaai.repository.ContratoRepository;
import com.compasso.meempregaai.repository.EmpregadoRepository;
import com.compasso.meempregaai.repository.EmpregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/")
public class ContratoController {

    @Autowired
    EmpregadoRepository empregadoRepository;

    @Autowired
    EmpregadorRepository empregadorRepository;

    @Autowired
    ContratoRepository contratoRepository;

    @GetMapping("empregado/{id}/contratos")
    public ResponseEntity<?> contratosEmpregado (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(id));

        if(optionalEmpregado.isPresent()) {
            Empregado empregado = optionalEmpregado.get();
            if(logado.getId().equals(empregado.getId()) && logado.getTipo().equals(empregado.getTipo())){
                List<Contrato> contratos = empregado.getContratos();
                return ResponseEntity.ok(ContratoDto.converter(contratos));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("empregador/{id}/contratos")
    public ResponseEntity<?> contratosEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(id));

        if(optionalEmpregador.isPresent()) {
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                List<Contrato> contratos = empregador.getContratos();
                return ResponseEntity.ok(ContratoDto.converter(contratos));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("empregador/contrato/{id}")
    @Transactional
    public ResponseEntity<?> inativarEmpregadoEmpregador (@PathVariable Long id, @AuthenticationPrincipal Usuario logado){

        Optional<Contrato> optionalEmpregadoEmpregador = Optional.ofNullable(contratoRepository.findById(id));

        if(optionalEmpregadoEmpregador.isPresent()) {
            Contrato contrato = optionalEmpregadoEmpregador.get();
            Empregador empregador = contrato.getEmpregador();

            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                contrato.setAtivo(false);
                contrato.setDataFinal(LocalDate.now());
                return ResponseEntity.ok(new ContratoDto(contrato));}
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("empregador/contratar")
    @Transactional
    @CacheEvict(value = "listaEmpregador",allEntries = true)
    public ResponseEntity<EmpregadorDto> contratarEmpregado (@RequestBody @Valid ContratoForm form, @AuthenticationPrincipal Usuario logado) {
        Optional<Empregador> optionalEmpregador = Optional.ofNullable(empregadorRepository.findById(form.getEmpregadorId()));
        Optional<Empregado> optionalEmpregado = Optional.ofNullable(empregadoRepository.findById(form.getEmpregadoId()));
        if(optionalEmpregador.isPresent() && optionalEmpregado.isPresent()){
            Empregador empregador = optionalEmpregador.get();
            if(logado.getId().equals(empregador.getId()) && logado.getTipo().equals(empregador.getTipo())){
                Empregado empregado = optionalEmpregado.get();

                Contrato contrato = form.converter(form, empregado, empregador);
                contratoRepository.save(contrato);

                return ResponseEntity.ok(new EmpregadorDto(empregador));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.notFound().build();
    }
}
