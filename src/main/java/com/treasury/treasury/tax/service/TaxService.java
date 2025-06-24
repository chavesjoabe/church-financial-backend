package com.treasury.treasury.tax.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.treasury.treasury.tax.dto.CreateTaxDto;
import com.treasury.treasury.tax.repository.TaxRepository;
import com.treasury.treasury.tax.schema.Tax;

@Service
public class TaxService {
  private TaxRepository taxRepository;

  public TaxService(TaxRepository taxRepository) {
    this.taxRepository = taxRepository;
  }

  public Tax getTaxes() {
    try {
      List<Tax> taxes = taxRepository.findAll();
      Tax response = taxes.get(0);
      return response;
    } catch (Exception e) {
      String errorMessage = "ERROR ON GET TAXES FROM DATABASE - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public Tax create(CreateTaxDto createTaxDto) {
    try {
      List<Tax> existingTaxes = this.taxRepository.findAll();
      if (existingTaxes.size() == 0) {
        Tax tax = new Tax(
            createTaxDto.getFirstLeaderPercentage(),
            createTaxDto.getSecondLeaderPercentage(),
            createTaxDto.getMainChurchPercentage(),
            createTaxDto.getMinistryPercentage(),
            createTaxDto.getMainLeaderPercentage(),
            createTaxDto.getTransferMainLeaderPercentage(),
            createTaxDto.getTransferMainChurchPercentage());
        return this.taxRepository.save(tax);
      }
      Tax existingTax = existingTaxes.get(0);

      existingTax.setFirstLeaderPercentage(createTaxDto.getFirstLeaderPercentage());
      existingTax.setSecondLeaderPercentage(createTaxDto.getSecondLeaderPercentage());
      existingTax.setMainChurchPercentage(createTaxDto.getMainChurchPercentage());
      existingTax.setMainLeaderPercentage(createTaxDto.getMainLeaderPercentage());
      existingTax.setMinistryPercentage(createTaxDto.getMinistryPercentage());
      existingTax.setTransferMainLeaderPercentage(createTaxDto.getTransferMainLeaderPercentage());
      existingTax.setTransferMainChurchPercentage(createTaxDto.getTransferMainChurchPercentage());

      return this.taxRepository.save(existingTax);
    } catch (Exception e) {
      String errorMessage = "ERROR ON CREATE NEW TAX - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }
}
