package com.treasury.treasury.balance.schema;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.treasury.treasury.balance.constants.PaymentMethods;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;
import com.treasury.treasury.balance.constants.BalanceTypes;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "balance")
public class Balance {

  @Id
  private String id;
  private BalanceTypes type;
  private Float value;
  private String responsible;
  private String responsibleName;
  private Instant createdAt;
  private Instant updatedAt;
  private BalanceStatus status;
  private Instant balanceDate;
  private PaymentMethods paymentMethod;
  private String description;
  private String approvedBy;
  private Instant approvedAt;
  private BalanceIncomingTypes incomingType;
  private String rejectedBy;
  private Instant rejectedAt;
  private String category;
  private String externalId;
}
