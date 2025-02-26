package com.treasury.treasury.balance.dto;

import java.util.List;

public class AccountingReportItemV2Dto {
  private List<AccountingReportItemDto> balances;
  private float churchFirstLeaderPercentageTotal;
  private float churchSecondLeaderPercentageTotal;
  private float mainChurchPercentageTotal;
  private float ministryPercentageTotal;
  private float mainLeaderPercentageTotal;
  private float total;

  public AccountingReportItemV2Dto() {
  }

  public AccountingReportItemV2Dto(AccountingReportItemBuilder builder) {
    this.balances = builder.balances;
    this.churchFirstLeaderPercentageTotal = builder.churchFirstLeaderPercentageTotal;
    this.churchSecondLeaderPercentageTotal = builder.churchSecondLeaderPercentageTotal;
    this.mainLeaderPercentageTotal = builder.mainLeaderPercentageTotal;
    this.mainChurchPercentageTotal = builder.mainChurchPercentageTotal;
    this.ministryPercentageTotal = builder.ministryPercentageTotal;
    this.total = builder.total;
  }


  public List<AccountingReportItemDto> getBalances() {
    return balances;
  }

  public void setBalances(List<AccountingReportItemDto> balances) {
    this.balances = balances;
  }

  public float getChurchFirstLeaderPercentageTotal() {
    return churchFirstLeaderPercentageTotal;
  }

  public void setChurchFirstLeaderPercentageTotal(Float churchFirstLeaderPercentageTotal) {
    this.churchFirstLeaderPercentageTotal = churchFirstLeaderPercentageTotal;
  }

  public float getChurchSecondLeaderPercentageTotal() {
    return churchSecondLeaderPercentageTotal;
  }

  public void setChurchSecondLeaderPercentageTotal(Float churchSecondLeaderPercentageTotal) {
    this.churchSecondLeaderPercentageTotal = churchSecondLeaderPercentageTotal;
  }

  public float getMainChurchPercentageTotal() {
    return mainChurchPercentageTotal;
  }

  public void setMainChurchPercentageTotal(Float mainChurchPercentageTotal) {
    this.mainChurchPercentageTotal = mainChurchPercentageTotal;
  }

  public float getMinistryPercentageTotal() {
    return ministryPercentageTotal;
  }

  public void setMinistryPercentageTotal(Float ministryPercentageTotal) {
    this.ministryPercentageTotal = ministryPercentageTotal;
  }

  public float getMainLeaderPercentageTotal() {
    return mainLeaderPercentageTotal;
  }

  public void setMainLeaderPercentageTotal(Float mainLeaderPercentageTotal) {
    this.mainLeaderPercentageTotal = mainLeaderPercentageTotal;
  }

  public float getTotal() {
    return total;
  }

  public void setTotal(float total) {
    this.total = total;
  }


  public static class AccountingReportItemBuilder {
    private List<AccountingReportItemDto> balances;
    private float churchFirstLeaderPercentageTotal;
    private float churchSecondLeaderPercentageTotal;
    private float mainChurchPercentageTotal;
    private float ministryPercentageTotal;
    private float mainLeaderPercentageTotal;
    private float total;

    public AccountingReportItemBuilder() {
    }

    public AccountingReportItemBuilder balances(List<AccountingReportItemDto> balances) {
      this.balances = balances;
      return this;
    }

    public AccountingReportItemBuilder churchFirstLeaderPercentage(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getChurchFirstLeaderPercentage)
          .reduce(0f, Float::sum);

      this.churchFirstLeaderPercentageTotal = result;

      return this;
    }

    public AccountingReportItemBuilder churchSecondLeaderPercentageTotal(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getChurchSecondLeaderPercentage)
          .reduce(0f, Float::sum);

      this.churchSecondLeaderPercentageTotal = result;

      return this;
    }

    public AccountingReportItemBuilder mainChurchPercentageTotal(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getMainChurchPercentage)
          .reduce(0f, Float::sum);

      this.mainChurchPercentageTotal = result;

      return this;
    }

    public AccountingReportItemBuilder mainLeaderPercentageTotal(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getMainLeaderPercentage)
          .reduce(0f, Float::sum);

      this.mainLeaderPercentageTotal = result;

      return this;
    }

    public AccountingReportItemBuilder ministryPercentageTotal(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getMinistryPercentage)
          .reduce(0f, Float::sum);

      this.ministryPercentageTotal = result;

      return this;
    }

    public AccountingReportItemBuilder total(List<AccountingReportItemDto> balances) {
      float result = this.balances
          .stream()
          .map(AccountingReportItemDto::getValue)
          .reduce(0f, Float::sum);

      this.total = result;

      return this;
    }

    public AccountingReportItemV2Dto build() {
      return new AccountingReportItemV2Dto(this);
    }
  }

  public static AccountingReportItemBuilder builder() {
    return new AccountingReportItemBuilder();
  }

}
