package com.treasury.treasury.tax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.treasury.treasury.tax.dto.CreateTaxDto;
import com.treasury.treasury.tax.repository.TaxRepository;
import com.treasury.treasury.tax.schema.Tax;
import com.treasury.treasury.tax.service.TaxService;

@ExtendWith(MockitoExtension.class)
public class TaxServiceTest {

  @Mock
  private TaxRepository taxRepository;

  @InjectMocks
  private TaxService taxService;

  private Tax sampleTax;
  private CreateTaxDto sampleTaxDto;

  @BeforeEach
  void setUp() {
    sampleTax = new Tax(10.0, 5.0, 20.0, 15.0, 30.0, 10.0, 10.0);
    sampleTaxDto = new CreateTaxDto(10.0, 5.0, 20.0, 15.0, 30.0, 10.0, 10.0);
  }

  @Test
  void shouldGetTaxesSuccessfully() {
    when(taxRepository.findAll()).thenReturn(Arrays.asList(sampleTax));

    Tax result = taxService.getTaxes();

    assertNotNull(result);
    assertEquals(10.0, result.getFirstLeaderPercentage());
    verify(taxRepository, times(1)).findAll();
  }

  @Test
  void shouldThrowRuntimeExceptionWhenGetTaxesFails() {
    when(taxRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        taxService.getTaxes());
    
    assertEquals("ERROR ON GET TAXES FROM DATABASE - DB Error", exception.getMessage());
  }

  @Test
  void shouldThrowRuntimeExceptionWhenNoTaxesFound() {
    when(taxRepository.findAll()).thenReturn(Collections.emptyList());

    assertThrows(RuntimeException.class, () -> 
        taxService.getTaxes());
  }

  @Test
  void shouldCreateNewTaxWhenRepositoryIsEmpty() {
    when(taxRepository.findAll()).thenReturn(Collections.emptyList());
    when(taxRepository.save(any(Tax.class))).thenReturn(sampleTax);

    Tax result = taxService.create(sampleTaxDto);

    assertNotNull(result);
    verify(taxRepository, times(1)).save(any(Tax.class));
  }

  @Test
  void shouldUpdateExistingTaxWhenRepositoryIsNotEmpty() {
    when(taxRepository.findAll()).thenReturn(Arrays.asList(sampleTax));
    when(taxRepository.save(any(Tax.class))).thenReturn(sampleTax);

    CreateTaxDto updateDto = new CreateTaxDto(12.0, 6.0, 22.0, 17.0, 32.0, 12.0, 12.0);
    Tax result = taxService.create(updateDto);

    assertNotNull(result);
    assertEquals(12.0, result.getFirstLeaderPercentage());
    verify(taxRepository, times(1)).save(sampleTax);
  }

  @Test
  void shouldThrowRuntimeExceptionWhenCreateFails() {
    when(taxRepository.findAll()).thenThrow(new RuntimeException("DB Error"));

    RuntimeException exception = assertThrows(RuntimeException.class, () -> 
        taxService.create(sampleTaxDto));
    
    assertEquals("ERROR ON CREATE NEW TAX - DB Error", exception.getMessage());
  }
}
