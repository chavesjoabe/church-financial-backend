package com.treasury.treasury.balance.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.dto.AccountingReportItemDto;
import com.treasury.treasury.balance.dto.BalanceDto;
import com.treasury.treasury.balance.repository.BalanceRepository;
import com.treasury.treasury.balance.schema.Balance;
import com.treasury.treasury.tax.schema.Tax;
import com.treasury.treasury.tax.service.TaxService;
import com.treasury.treasury.user.constants.UserRoles;

@Service
public class BalanceService {

  private final BalanceRepository balanceRepository;
  private final TaxService taxService;

  public BalanceService(
      BalanceRepository balanceRepository,
      TaxService taxService) {
    this.balanceRepository = balanceRepository;
    this.taxService = taxService;
  }

  public Balance create(
      BalanceDto balanceDto,
      String loggedUserDocument,
      String loggedUserRole) {

    Balance balance = new Balance(
        balanceDto.getType(),
        balanceDto.getValue(),
        loggedUserDocument,
        balanceDto.getBalanceDate(),
        balanceDto.getDescription(),
        balanceDto.getFreeDescription(),
        balanceDto.getIncomingType());

    return this.balanceRepository.save(balance);
  }

  public List<Balance> findAll() {
    return this.balanceRepository.findAll();
  }

  public Balance findById(String id) {
    try {
      return this.balanceRepository.findById(id).orElseThrow(() -> new RuntimeException("Balance not found"));
    } catch (Exception e) {
      String errorMessage = "Error on find Balance by ID " + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  @Transactional
  public Balance approveBalance(String id, String loggedUserDocument, String loggedUserRole) {
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
        String errorMessage = "CANNOT REJECT AN APPROVED BALANCE";
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
      String errorMessage = "ERROR ON REJECT BALANCE" + e.getMessage();
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
      String errorMessage = "ERROR ON REJECT BALANCE" + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public List<Balance> findAllBalancesByDate(Instant startDate, Instant endDate) {
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

  public List<AccountingReportItemDto> extractAccountingReport(Instant startDate, Instant endDate) {
    try {
      List<Balance> balances = this.balanceRepository
          .findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
              startDate,
              endDate,
              BalanceStatus.APPROVED,
              BalanceTypes.INCOMING,
              BalanceIncomingTypes.OFICIAL);

      Tax tax = taxService.getTaxes();
      List<AccountingReportItemDto> response = balances.stream()
          .map(balance -> new AccountingReportItemDto(balance, tax))
          .toList();
      return response;
    } catch (Exception e) {
      String errorMessage = "ERROR ON EXTRACT BALANCE" + e.getMessage();
      throw new RuntimeException(errorMessage);
    }
  }

  public List<Balance> findAllPendingBalances(String loggedUserDocument, String loggedUserRole) {
    if (UserRoles.COMMON.toString().equals(loggedUserRole)) {
      return this.balanceRepository.findByStatusAndResponsibleOrderByBalanceDate(BalanceStatus.PENDING,
          loggedUserDocument);
    }

    return this.balanceRepository.findByStatusOrderByBalanceDate(BalanceStatus.PENDING);
  }
}
