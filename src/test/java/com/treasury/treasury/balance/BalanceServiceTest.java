package com.treasury.treasury.balance;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.treasury.treasury.balance.constants.PaymentMethods;
import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.dto.BalanceDto;
import com.treasury.treasury.balance.repository.BalanceRepository;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.balance.service.BalanceService;
import com.treasury.treasury.commons.loggerService.LoggerService;
import com.treasury.treasury.tax.service.TaxService;
import com.treasury.treasury.user.constants.UserRoles;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

  @Mock
  private BalanceRepository balanceRepository;

  @Mock
  private TaxService taxService;

  @Mock
  private LoggerService logger;

  @InjectMocks
  private BalanceService balanceService;

  @Test
  public void ShouldCreateANewBalance() {
    Balance sampleBalance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(sampleBalance);

    BalanceDto balanceDto = new BalanceDto(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        Instant.now(),
        "Free description",
        BalanceIncomingTypes.OFICIAL,
        "TESTE",
        PaymentMethods.PIX);

    Balance result = balanceService.create(balanceDto, "", "", "");

    assertEquals(result.getValue(), 333);
    Mockito.verify(logger, Mockito.times(1))
        .info(any(), any());

    Mockito.verify(balanceRepository, Mockito.times(1))
        .save(any());
  }

  public void ShouldCreateBalanceAndGetResponsibleFromParameter() {
    Balance sampleBalance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(sampleBalance);

    BalanceDto balanceDto = new BalanceDto(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        Instant.now(),
        "Free description",
        BalanceIncomingTypes.OFICIAL,
        "TESTE",
        PaymentMethods.PIX);

    String loggedDocument = "123123";
    String loggedUserName = "TEST";

    Balance result = balanceService.create(balanceDto, loggedDocument, "", loggedUserName);

    assertEquals(result.getValue(), 333);
    assertEquals(result.getResponsible(), loggedDocument);
    assertEquals(result.getResponsibleName(), loggedUserName);
  }

  @Test
  public void ShouldReturnAllPendingBalances() {
    Balance balance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);
    List<Balance> mockBalances = Arrays.asList(balance);
    // mock method
    when(balanceRepository.findByStatusOrderByBalanceDate(BalanceStatus.PENDING)).thenReturn(mockBalances);
    // action
    List<Balance> result = balanceService.findAllPendingBalances("123123123",
        "ADMIN");
    // asserts
    assertEquals(1, result.size());
    verify(balanceRepository, times(1))
        .findByStatusOrderByBalanceDate(BalanceStatus.PENDING);
  }

  @Test
  public void ShouldReturnAllBalancesByDate() {
    Balance balance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    Balance balance2 = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    List<Balance> mockBalances = Arrays.asList(balance, balance2);

    Instant startDate = Instant.now();
    Instant endDate = Instant.now();

    when(balanceRepository.findByBalanceDateBetweenOrderByBalanceDate(startDate, endDate)).thenReturn(mockBalances);

    List<Balance> result = balanceService.findAllByDate(startDate, endDate, "TESTE", "TESTE");

    assertEquals(2, result.size());
    verify(balanceRepository, times(1))
        .findByBalanceDateBetweenOrderByBalanceDate(startDate, endDate);
  }

  @Test
  public void ShouldFindAllBalances() {
    Balance balance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    Balance balance2 = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    List<Balance> mockBalances = Arrays.asList(balance, balance2);
    when(balanceRepository.findAll()).thenReturn(mockBalances);

    List<Balance> result = balanceService.findAll();

    assertEquals(2, result.size());
    verify(balanceRepository, times(1)).findAll();
  }

  @Test
  public void ShouldApproveBalance() {
    Balance sampleBalance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    when(balanceRepository.findById("123")).thenReturn(Optional.of(sampleBalance));
    when(balanceRepository.save(any(Balance.class))).thenReturn(sampleBalance);

    Balance response = balanceService.approveBalance("123", "TESTE 2", UserRoles.ADMIN.toString());
    assertEquals(response.getStatus(), BalanceStatus.APPROVED);
  }

  @Test
  public void ShouldRejectBalance() {
    Balance sampleBalance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    when(balanceRepository.findById("123"))
        .thenReturn(Optional.of(sampleBalance));

    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(sampleBalance);

    Balance response = balanceService
        .rejectBalance("123", "TESTE 2", UserRoles.ADMIN.toString());

    assertEquals(response.getStatus(), BalanceStatus.REJECTED);
  }

  @Test
  public void ShouldRemoveBalance() {
    Balance sampleBalance = new Balance(
        BalanceTypes.INCOMING,
        (float) 333,
        "TESTE",
        "TESTE",
        Instant.now(),
        PaymentMethods.PIX,
        "Free description",
        "TESTE",
        BalanceIncomingTypes.OFICIAL);

    when(balanceRepository.findById("123"))
        .thenReturn(Optional.of(sampleBalance));
    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(sampleBalance);

    Balance response = balanceService.removeBalance("123", "TESTE 2", UserRoles.ADMIN.toString());
    assertEquals(response.getStatus(), BalanceStatus.REMOVED);
  }
}
