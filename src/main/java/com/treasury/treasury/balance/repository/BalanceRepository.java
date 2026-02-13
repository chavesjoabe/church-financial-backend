package com.treasury.treasury.balance.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;
import com.treasury.treasury.balance.schema.Balance;

public interface BalanceRepository extends MongoRepository<Balance, String> {
  List<Balance> findByBalanceDateBetweenAndStatusOrderByBalanceDate(Instant startDate, Instant endDate,
      BalanceStatus balanceStatus);

  List<Balance> findByBalanceDateBetweenOrderByBalanceDate(
      Instant startDate,
      Instant endDate);

  List<Balance> findByBalanceDateBetweenAndResponsibleOrderByBalanceDate(
      Instant startDate,
      Instant endDate,
      String loggedUserDocument);

  List<Balance> findByBalanceDateBetweenAndStatusAndTypeOrderByBalanceDate(
      Instant startDate,
      Instant endDate,
      BalanceStatus balanceStatus,
      BalanceTypes balanceType);

  List<Balance> findByBalanceDateBetweenAndStatusAndTypeAndIncomingTypeOrderByBalanceDate(
      Instant startDate,
      Instant endDate,
      BalanceStatus balanceStatus,
      BalanceTypes balanceType,
      BalanceIncomingTypes incomingType);

  List<Balance> findByStatusOrderByBalanceDate(BalanceStatus status);

  List<Balance> findByStatusAndResponsibleOrderByBalanceDate(BalanceStatus status, String responsible);

  Optional<Balance> findByExternalId(String externalId);
}
