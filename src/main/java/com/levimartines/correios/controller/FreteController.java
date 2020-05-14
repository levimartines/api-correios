package com.levimartines.correios.controller;

import com.levimartines.correios.service.CorreiosService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1")
@CrossOrigin(origins = "https://levi-dev.myshopify.com/")
public class FreteController {

    @Autowired
    private CorreiosService correiosService;

    @GetMapping("/frete/{cep}")
    public ResponseEntity<Double> getValorFrete(@PathVariable("cep") String cep) {
        log.info(cep);
        return ResponseEntity.ok(correiosService.getValorFrete(cep));
    }
}
