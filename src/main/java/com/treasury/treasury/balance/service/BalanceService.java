package com.treasury.treasury.balance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.constants.PaymentMethods;
import com.treasury.treasury.balance.dto.AccountingReportItemDto;
import com.treasury.treasury.balance.dto.AccountingReportItemV2Dto;
import com.treasury.treasury.balance.dto.BalanceDto;
import com.treasury.treasury.balance.exceptions.OfxCreationException;
import com.treasury.treasury.balance.repository.BalanceRepository;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.commons.loggerService.LoggerService;
import com.treasury.treasury.tax.schema.Tax;
import com.treasury.treasury.tax.service.TaxService;
import com.treasury.treasury.user.constants.UserRoles;
import com.webcohesion.ofx4j.domain.data.ResponseEnvelope;
import com.webcohesion.ofx4j.domain.data.banking.BankStatementResponseTransaction;
import com.webcohesion.ofx4j.domain.data.common.Transaction;
import com.webcohesion.ofx4j.io.AggregateUnmarshaller;

@Service
public class BalanceService {

  private final BalanceRepository balanceRepository;
  private final TaxService taxService;
  private final LoggerService logger;

  public BalanceService(
      BalanceRepository balanceRepository,
      TaxService taxService,
      LoggerService logger) {

    this.balanceRepository = balanceRepository;
    this.taxService = taxService;
    this.logger = logger;
  }

  public Balance create(
      BalanceDto balanceDto,
      String loggedUserDocument,
      String loggedUserRole,
      String loggedUserName) {

    Balance balance = Balance
        .builder()
        .id(UUID.randomUUID().toString())
        .type(balanceDto.getType())
        .value(balanceDto.getValue())
        .responsible(loggedUserDocument)
        .responsibleName(loggedUserName)
        .balanceDate(balanceDto.getBalanceDate())
        .paymentMethod(balanceDto.getPaymentMethod())
        .description(balanceDto.getDescription())
        .category(balanceDto.getCategory())
        .incomingType(balanceDto.getIncomingType())
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .status(BalanceStatus.PENDING)
        .build();

    logger.info(
        BalanceService.class,
        "new balance with id - " + balance.getId() + " created with success");

    return this.balanceRepository.save(balance);
  }

  public List<Balance> findAll() {
    return this.balanceRepository.findAll();
  }

  public List<Balance> findAllByDate(
      Instant startDate,
      Instant endDate,
      String loggedUserDocument,
      String loggedUserRole) {

    if (UserRoles.COMMON.toString().equals(loggedUserRole)) {
      return this.balanceRepository.findByBalanceDateBetweenAndResponsibleOrderByBalanceDate(
          startDate,
          endDate,
          loggedUserDocument);

    }

    return this.balanceRepository.findByBalanceDateBetweenOrderByBalanceDate(startDate, endDate);
  }

  public Balance findById(String id) {
    try {
      return this.balanceRepository
          .findById(id)
          .orElseThrow(() -> new RuntimeException("Balance not found"));
    } catch (Exception e) {
      String errorMessage = "Error on find Balance by ID " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  @Transactional
  public Balance approveBalance(
      String id,
      String loggedUserDocument,
      String loggedUserRole) {
    try {
      Balance balance = this.balanceRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Balance not found"));

      if (balance.getResponsible().equals(loggedUserDocument)) {
        String errorMessage = "BALANCE RESPONSIBLE AND BALANCE APPROVER CANNOT BE THE SAME USER";
        throw new RuntimeException(errorMessage);
      }

      balance.setStatus(BalanceStatus.APPROVED);
      balance.setApprovedBy(loggedUserDocument);
      balance.setApprovedAt(Instant.now());
      this.balanceRepository.save(balance);
      return balance;
    } catch (Exception e) {
      String errorMessage = "Error on approve balance " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  @Transactional
  public Balance rejectBalance(String id, String loggedUserDocument, String loggedUserRole) {
    try {
      Balance balance = this.balanceRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Balance not found"));

      if (balance.getStatus().toString().equals(BalanceStatus.APPROVED.toString())) {
        String errorMessage = "CANNOT REJECT AN APPROVED BALANCE ";
        throw new RuntimeException(errorMessage);
      }

      if (UserRoles.COMMON.toString().equals(loggedUserRole) && !balance.getResponsible().equals(loggedUserDocument)) {
        String errorMessage = "USER WITH ROLE [" + loggedUserRole + "] CANNOT REJECT A BALANCE OF ANOTHER USER";
        throw new RuntimeException(errorMessage);
      }

      balance.setStatus(BalanceStatus.REJECTED);
      balance.setRejectedBy(loggedUserDocument);
      balance.setRejectedAt(Instant.now());
      this.balanceRepository.save(balance);
      return balance;
    } catch (Exception e) {
      String errorMessage = "ERROR ON REJECT BALANCE - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  @Transactional
  public Balance removeBalance(String id, String loggedUserDocument, String loggedUserRole) {
    try {
      Balance balance = this.balanceRepository.findById(id)
          .orElseThrow(() -> new RuntimeException("Balance not found"));

      if (balance.getStatus().toString().equals(BalanceStatus.APPROVED.toString())) {
        String errorMessage = "CANNOT REJECT AN APPROVED BALANCE";
        throw new RuntimeException(errorMessage);
      }

      if (UserRoles.COMMON.toString().equals(loggedUserRole) && !balance.getResponsible().equals(loggedUserDocument)) {
        String errorMessage = "USER WITH ROLE [" + loggedUserRole + "] CANNOT REJECT A BALANCE OF ANOTHER USER";
        throw new RuntimeException(errorMessage);
      }

      balance.setStatus(BalanceStatus.REMOVED);
      balance.setRejectedBy(loggedUserDocument);
      balance.setRejectedAt(Instant.now());
      this.balanceRepository.save(balance);
      return balance;
    } catch (Exception e) {
      String errorMessage = "ERROR ON REJECT BALANCE - " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public List<Balance> findAllApprovedBalancesByDate(Instant startDate, Instant endDate) {
    return this.balanceRepository.findByBalanceDateBetweenAndStatusOrderByBalanceDate(
        startDate,
        endDate,
        BalanceStatus.APPROVED);
  }

  public List<Balance> findAllIncomingBalancesByDate(Instant startDate, Instant endDate) {
    return this.balanceRepository
        .findByBalanceDateBetweenAndStatusAndTypeOrderByBalanceDate(
            startDate,
            endDate,
            BalanceStatus.APPROVED,
            BalanceTypes.INCOMING);
  }

  public List<Balance> findAllOutgoingBalancesByDate(Instant startDate, Instant endDate) {
    return this.balanceRepository
        .findByBalanceDateBetweenAndStatusAndTypeOrderByBalanceDate(
            startDate,
            endDate,
            BalanceStatus.APPROVED,
            BalanceTypes.OUTGOING);
  }

  public AccountingReportItemV2Dto extractAccountingReportV2(Instant startDate, Instant endDate) {
    try {
      List<Balance> balances = this.balanceRepository
          .findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
              startDate,
              endDate,
              BalanceStatus.APPROVED,
              BalanceTypes.INCOMING,
              BalanceIncomingTypes.OFICIAL);

      List<Balance> transferBalances = this.balanceRepository
          .findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
              startDate,
              endDate,
              BalanceStatus.APPROVED,
              BalanceTypes.INCOMING,
              BalanceIncomingTypes.TRANSFER);

      List<Balance> transferGeolBalances = this.balanceRepository
          .findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
              startDate,
              endDate,
              BalanceStatus.APPROVED,
              BalanceTypes.INCOMING,
              BalanceIncomingTypes.TRANSFER_GEOL);

      List<Balance> nonOficialBalances = this.balanceRepository
          .findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
              startDate,
              endDate,
              BalanceStatus.APPROVED,
              BalanceTypes.INCOMING,
              BalanceIncomingTypes.NON_OFICIAL);

      Tax tax = taxService.getTaxes();

      List<AccountingReportItemDto> responseTransferBalances = transferBalances.stream()
          .map(balance -> new AccountingReportItemDto(balance, tax))
          .toList();

      List<AccountingReportItemDto> responseBalances = balances.stream()
          .map(balance -> new AccountingReportItemDto(balance, tax))
          .toList();

      List<AccountingReportItemDto> responseTransferGeolBalances = transferGeolBalances.stream()
          .map(balance -> new AccountingReportItemDto(balance, tax))
          .toList();

      return AccountingReportItemV2Dto
          .builder()
          .balances(responseBalances)
          .transferBalances(responseTransferBalances)
          .transferGeolBalances(responseTransferGeolBalances)
          .nonOficialBalances(nonOficialBalances)
          .transferBalancesTotal(responseTransferBalances)
          .transferGeolBalancesTotal(responseTransferGeolBalances)
          .balancesTotal(responseBalances)
          .build();
    } catch (Exception exception) {
      String errorMessage = "ERROR ON EXTRACT BALANCE" + exception.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public List<Balance> findAllPendingBalances(String loggedUserDocument, String loggedUserRole) {
    if (UserRoles.COMMON.toString().equals(loggedUserRole)) {
      return this.balanceRepository
          .findByStatusAndResponsibleOrderByBalanceDate(
              BalanceStatus.PENDING,
              loggedUserDocument);
    }

    return this.balanceRepository.findByStatusOrderByBalanceDate(BalanceStatus.PENDING);
  }

  public List<Balance> processBalancesByOfxFile(
      MultipartFile file,
      String loggedUserName,
      String loggedUserDocument) {
    try {

      AggregateUnmarshaller<ResponseEnvelope> aggregateUnmarshaller = new AggregateUnmarshaller<>(
          ResponseEnvelope.class);

      ResponseEnvelope result = aggregateUnmarshaller.unmarshal(file.getInputStream());

      BankStatementResponseTransaction bankStatementResponseTransaction = (BankStatementResponseTransaction) result
          .getMessageSets().getLast()
          .getResponseMessages().getFirst();

      List<Transaction> transactions = bankStatementResponseTransaction
          .getMessage()
          .getTransactionList()
          .getTransactions();

      if (transactions.isEmpty()) {
        logger.info(BalanceService.class, "No balances to process from OFX file");
        return null;
      }

      List<Balance> response = new ArrayList<>();
      AtomicInteger counter = new AtomicInteger(1);

      transactions.stream().forEach(transaction -> {
        logger.info(this.getClass(), "processing item " + counter + "/" + transactions.size());

        Balance balance = Balance
            .builder()
            .id(UUID.randomUUID().toString())
            .type(transaction.getAmount() > 0 ? BalanceTypes.INCOMING : BalanceTypes.OUTGOING)
            .value((float) Math.abs(transaction.getAmount()))
            .responsible(loggedUserDocument)
            .responsibleName(loggedUserName)
            .balanceDate(transaction.getDatePosted().toInstant())
            .paymentMethod(PaymentMethods.MONEY)
            .description(transaction.getMemo())
            .category("IMPORTAÇAO EXTRATO")
            .incomingType(BalanceIncomingTypes.OFICIAL)
            .createdAt(Instant.now())
            .updatedAt(Instant.now())
            .status(BalanceStatus.PENDING)
            .externalId(transaction.getId())
            .build();

        Optional<Balance> existentBalance = balanceRepository
            .findByExternalId(balance.getExternalId());

        if (existentBalance.isPresent()) {
          logger.info(this.getClass(), "Balance with external id - " + balance.getExternalId() + " already exists");
          counter.incrementAndGet();
        } else {
          balanceRepository.save(balance);
          response.add(balance);
          counter.incrementAndGet();
        }
      });

      logger.info(BalanceService.class, "Total of: " + response.size() + " balances imported via OFX file");

      return response;
    } catch (Exception exception) {
      logger.error(BalanceService.class, "ERROR ON PROCESS OFX FILE");
      throw new OfxCreationException();
    }
  }

  public List<String> approveOrRejectMassive(List<String> ids, BalanceStatus status, String loggedUserDocument) {
    List<String> updatedIds = new ArrayList<>();

    ids.stream().forEach(balanceId -> {
      try {
        Balance balance = balanceRepository.findById(balanceId).get();
        balance.setStatus(status);

        if (!balance.getResponsible().equals(loggedUserDocument)) {
          balanceRepository.save(balance);
          updatedIds.add(balanceId);
        }
      } catch (Exception exception) {
        logger.error(BalanceService.class, "ERROR ON UPDATE BALANCE WITH ID - " + balanceId);
      }
    });

    logger.info(BalanceService.class, "TOTAL OF " + updatedIds.size() + " BALANCES UPDATED");

    return updatedIds;
  }
}
