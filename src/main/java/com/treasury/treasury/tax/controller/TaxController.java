package com.treasury.treasury.tax.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.treasury.treasury.tax.dto.CreateTaxDto;
import com.treasury.treasury.tax.schema.Tax;
import com.treasury.treasury.tax.service.TaxService;

@RestController
@RequestMapping("api/tax")
@CrossOrigin(origins = "*")
public class TaxController {
  private TaxService taxService;

  public TaxController(TaxService taxService) {
    this.taxService = taxService;
  }

  @GetMapping("/all")
  public ResponseEntity<Tax> getTaxes() {
    Tax response = this.taxService.getTaxes();
    return new ResponseEntity<Tax>(response, HttpStatus.OK);
  }

  @PostMapping("/create")
  public ResponseEntity<Tax> createTax(@RequestBody CreateTaxDto createTaxDto) {
    Tax response = this.taxService.create(createTaxDto);
    return new ResponseEntity<Tax>(response, HttpStatus.CREATED);
  }
}
