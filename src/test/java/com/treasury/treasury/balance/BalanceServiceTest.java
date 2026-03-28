package com.treasury.treasury.balance;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import com.treasury.treasury.balance.constants.PaymentMethods;
import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.dto.AccountingReportItemV2Dto;
import com.treasury.treasury.balance.dto.BalanceDto;
import com.treasury.treasury.balance.dto.UpdateBalanceDto;
import com.treasury.treasury.balance.dto.DashboardDataDto;
import com.treasury.treasury.balance.exceptions.OfxCreationException;
import com.treasury.treasury.balance.repository.BalanceRepository;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.balance.service.BalanceService;
import com.treasury.treasury.commons.loggerService.LoggerService;
import com.treasury.treasury.tax.schema.Tax;
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
    Balance sampleBalance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

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

  @Test
  public void ShouldCreateBalanceAndGetResponsibleFromParameter() {
    Balance sampleBalance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("123123")
        .responsibleName("TEST")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

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

    String loggedUserDocument = "123123";
    String loggedUserName = "TEST";

    Balance result = balanceService.create(balanceDto, loggedUserDocument, "", loggedUserName);

    assertEquals(result.getValue(), 333);
    assertEquals(result.getResponsible(), loggedUserDocument);
    assertEquals(result.getResponsibleName(), loggedUserName);
  }

  @Test
  public void ShouldReturnAllPendingBalances() {
    Balance balance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

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
    Balance balance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    Balance balance2 = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

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
    Balance balance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    Balance balance2 = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    List<Balance> mockBalances = Arrays.asList(balance, balance2);
    when(balanceRepository.findAll()).thenReturn(mockBalances);

    List<Balance> result = balanceService.findAll();

    assertEquals(2, result.size());
    verify(balanceRepository, times(1)).findAll();
  }

  @Test
  public void ShouldUpdateBalance() {
    Balance sampleBalance = Balance
        .builder()
        .id("123")
        .status(BalanceStatus.PENDING)
        .category("OLD")
        .description("OLD")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    when(balanceRepository.findById("123")).thenReturn(Optional.of(sampleBalance));
    when(balanceRepository.save(any(Balance.class))).thenReturn(sampleBalance);

    UpdateBalanceDto updateDto = new UpdateBalanceDto(
        BalanceStatus.PENDING,
        BalanceIncomingTypes.NON_OFICIAL,
        "NEW CATEGORY",
        "NEW DESCRIPTION");

    Balance result = balanceService.update("123", updateDto);

    assertEquals(BalanceIncomingTypes.NON_OFICIAL, result.getIncomingType());
    assertEquals("NEW CATEGORY", result.getCategory());
    assertEquals("NEW DESCRIPTION", result.getDescription());
    verify(balanceRepository).save(any(Balance.class));
  }

  @Test
  public void ShouldThrowExceptionWhenUpdateBalanceNotPending() {
    Balance sampleBalance = Balance
        .builder()
        .id("123")
        .status(BalanceStatus.APPROVED)
        .build();

    when(balanceRepository.findById("123")).thenReturn(Optional.of(sampleBalance));

    UpdateBalanceDto updateDto = new UpdateBalanceDto(
        BalanceStatus.PENDING,
        BalanceIncomingTypes.NON_OFICIAL,
        "NEW CATEGORY",
        "NEW DESCRIPTION");

    assertThrows(RuntimeException.class, () -> balanceService.update("123", updateDto));
    verify(balanceRepository, never()).save(any(Balance.class));
  }

  @Test
  public void ShouldApproveBalance() {
    Balance sampleBalance = Balance
        .builder()
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    when(balanceRepository.findById("123")).thenReturn(Optional.of(sampleBalance));
    when(balanceRepository.save(any(Balance.class))).thenReturn(sampleBalance);

    Balance response = balanceService.approveBalance("123", "TESTE 2", UserRoles.ADMIN.toString());
    assertEquals(response.getStatus(), BalanceStatus.APPROVED);
  }

  @Test
  public void ShouldRejectBalance() {
    Balance sampleBalance = Balance
        .builder()
        .id("123")
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .status(BalanceStatus.PENDING)
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

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
    Balance sampleBalance = Balance
        .builder()
        .id("123")
        .type(BalanceTypes.INCOMING)
        .value((float) 333)
        .responsible("TESTE")
        .responsibleName("TESTE")
        .balanceDate(Instant.now())
        .paymentMethod(PaymentMethods.PIX)
        .description("Free description")
        .status(BalanceStatus.PENDING)
        .category("TESTE")
        .incomingType(BalanceIncomingTypes.OFICIAL)
        .build();

    when(balanceRepository.findById("123"))
        .thenReturn(Optional.of(sampleBalance));
    when(balanceRepository.save(any(Balance.class)))
        .thenReturn(sampleBalance);

    Balance response = balanceService.removeBalance(
        "123",
        "TESTE 2",
        UserRoles.ADMIN.toString());

    assertEquals(response.getStatus(), BalanceStatus.REMOVED);
  }

  @Test
  public void ShouldReturnDashboardData() {
    Balance incoming = Balance.builder()
        .type(BalanceTypes.INCOMING)
        .value(1000f)
        .status(BalanceStatus.APPROVED)
        .build();
    Balance outgoing = Balance.builder()
        .type(BalanceTypes.OUTGOING)
        .value(400f)
        .status(BalanceStatus.APPROVED)
        .build();

    when(balanceRepository.findByBalanceDateBetweenAndStatusOrderByBalanceDate(any(), any(), eq(BalanceStatus.APPROVED)))
        .thenReturn(Arrays.asList(incoming, outgoing));

    DashboardDataDto result = balanceService.getDashboardData();

    assertEquals(1000f, result.totalIncomings());
    assertEquals(400f, result.totalOutgoings());
    assertEquals(600f, result.totalBalance());
  }

  @Test
  public void ShouldReturnAllBalancesByDateForCommonUser() {
    Instant startDate = Instant.now();
    Instant endDate = Instant.now();
    String loggedUserDocument = "123";
    String loggedUserRole = UserRoles.COMMON.toString();

    balanceService.findAllByDate(startDate, endDate, loggedUserDocument, loggedUserRole);

    verify(balanceRepository, times(1))
        .findByBalanceDateBetweenAndResponsibleOrderByBalanceDate(startDate, endDate, loggedUserDocument);
  }

  @Test
  public void ShouldFindById() {
    Balance balance = Balance.builder().id("123").build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    Balance result = balanceService.findById("123");

    assertEquals("123", result.getId());
  }

  @Test
  public void ShouldThrowExceptionWhenFindByIdNotFound() {
    when(balanceRepository.findById("123")).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> balanceService.findById("123"));
  }

  @Test
  public void ShouldThrowExceptionWhenApproveBalanceSameUser() {
    Balance balance = Balance.builder().responsible("123").build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    assertThrows(RuntimeException.class, () -> balanceService.approveBalance("123", "123", "ADMIN"));
  }

  @Test
  public void ShouldThrowExceptionWhenRejectApprovedBalance() {
    Balance balance = Balance.builder().status(BalanceStatus.APPROVED).build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    assertThrows(RuntimeException.class, () -> balanceService.rejectBalance("123", "456", "ADMIN"));
  }

  @Test
  public void ShouldThrowExceptionWhenCommonUserRejectsOthersBalance() {
    Balance balance = Balance.builder().responsible("123").status(BalanceStatus.PENDING).build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    assertThrows(RuntimeException.class, () -> balanceService.rejectBalance("123", "456", UserRoles.COMMON.toString()));
  }

  @Test
  public void ShouldThrowExceptionWhenRemoveApprovedBalance() {
    Balance balance = Balance.builder().status(BalanceStatus.APPROVED).build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    assertThrows(RuntimeException.class, () -> balanceService.removeBalance("123", "456", "ADMIN"));
  }

  @Test
  public void ShouldThrowExceptionWhenCommonUserRemovesOthersBalance() {
    Balance balance = Balance.builder().responsible("123").status(BalanceStatus.PENDING).build();
    when(balanceRepository.findById("123")).thenReturn(Optional.of(balance));

    assertThrows(RuntimeException.class, () -> balanceService.removeBalance("123", "456", UserRoles.COMMON.toString()));
  }

  @Test
  public void ShouldFindAllApprovedBalancesByDate() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    balanceService.findAllApprovedBalancesByDate(start, end);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusOrderByBalanceDate(start, end, BalanceStatus.APPROVED);
  }

  @Test
  public void ShouldFindAllIncomingBalancesByDate() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    balanceService.findAllIncomingBalancesByDate(start, end);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.INCOMING);
  }

  @Test
  public void ShouldFindAllOutgoingBalancesByDate() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    balanceService.findAllOutgoingBalancesByDate(start, end);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.OUTGOING);
  }

  @Test
  public void ShouldReturnAllPendingBalancesForCommonUser() {
    String loggedUserDocument = "123";
    String loggedUserRole = UserRoles.COMMON.toString();

    balanceService.findAllPendingBalances(loggedUserDocument, loggedUserRole);

    verify(balanceRepository, times(1))
        .findByStatusAndResponsibleOrderByBalanceDate(BalanceStatus.PENDING, loggedUserDocument);
  }

  @Test
  public void ShouldApproveOrRejectMassive() {
    Balance balance1 = Balance.builder().id("1").responsible("user1").build();
    Balance balance2 = Balance.builder().id("2").responsible("user1").build();

    when(balanceRepository.findById("1")).thenReturn(Optional.of(balance1));
    when(balanceRepository.findById("2")).thenReturn(Optional.of(balance2));

    List<String> ids = Arrays.asList("1", "2");
    List<String> result = balanceService.approveOrRejectMassive(ids, BalanceStatus.APPROVED, "user2");

    assertEquals(2, result.size());
    verify(balanceRepository, times(2)).save(any(Balance.class));
  }

  @Test
  public void ShouldNotUpdateBalanceInMassiveWhenResponsibleIsApprover() {
    Balance balance1 = Balance.builder().id("1").responsible("user1").build();
    when(balanceRepository.findById("1")).thenReturn(Optional.of(balance1));

    List<String> ids = Arrays.asList("1");
    List<String> result = balanceService.approveOrRejectMassive(ids, BalanceStatus.APPROVED, "user1");

    assertEquals(0, result.size());
    verify(balanceRepository, never()).save(any(Balance.class));
  }

  @Test
  public void ShouldLogAndContinueInMassiveWhenErrorOccurs() {
    when(balanceRepository.findById("1")).thenThrow(new RuntimeException("DB error"));

    List<String> ids = Arrays.asList("1");
    List<String> result = balanceService.approveOrRejectMassive(ids, BalanceStatus.APPROVED, "user1");

    assertEquals(0, result.size());
    verify(logger).error(eq(BalanceService.class), contains("ERROR ON UPDATE BALANCE WITH ID - 1"));
  }

  @Test
  public void ShouldExtractAccountingReportV2() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    Tax mockTax = mock(Tax.class);
    when(taxService.getTaxes()).thenReturn(mockTax);

    AccountingReportItemV2Dto result = balanceService.extractAccountingReportV2(start, end);

    assertNotNull(result);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.INCOMING, BalanceIncomingTypes.OFICIAL);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.INCOMING, BalanceIncomingTypes.TRANSFER);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.INCOMING, BalanceIncomingTypes.TRANSFER_GEOL);
    verify(balanceRepository).findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(start, end, BalanceStatus.APPROVED, BalanceTypes.INCOMING, BalanceIncomingTypes.NON_OFICIAL);
  }

  @Test
  public void ShouldExtractAccountingReportV2WithData() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    Tax mockTax = mock(Tax.class);
    when(taxService.getTaxes()).thenReturn(mockTax);

    Balance b1 = Balance.builder().value(100f).build();
    when(balanceRepository.findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(any(), any(), any(), any(), eq(BalanceIncomingTypes.OFICIAL)))
        .thenReturn(Arrays.asList(b1));

    AccountingReportItemV2Dto result = balanceService.extractAccountingReportV2(start, end);

    assertNotNull(result);
    assertEquals(1, result.getBalances().size());
  }

  @Test
  public void ShouldThrowExceptionWhenExtractAccountingReportV2Fails() {
    Instant start = Instant.now();
    Instant end = Instant.now();
    when(balanceRepository.findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(any(), any(), any(), any(), any()))
        .thenThrow(new RuntimeException("DB Error"));

    assertThrows(RuntimeException.class, () -> balanceService.extractAccountingReportV2(start, end));
  }

  @Test
  public void ShouldThrowOfxCreationExceptionOnInvalidFile() throws Exception {
    MultipartFile file = mock(MultipartFile.class);
    when(file.getInputStream()).thenThrow(new RuntimeException("Stream error"));

    assertThrows(OfxCreationException.class, () -> balanceService.processBalancesByOfxFile(file, "name", "doc"));
  }
}
