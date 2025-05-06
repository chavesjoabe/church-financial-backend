package com.treasury.treasury.balance.dto;

import java.util.List;

public class AccountingReportItemV2Dto {
  private List<AccountingReportItemDto> balances;
  private float churchFirstLeaderPercentageTotal;
  private float churchSecondLeaderPercentageTotal;
  private float mainChurchPercentageTotal;
  private float ministryPercentageTotal;
  private float mainLeaderPercentageTotal;
  private List<AccountingReportItemDto> transferBalances;
  private List<AccountingReportItemDto> transferGeolBalances;
  private float total;
  private float transferBalancesTotal;
  private float transferGeolBalancesTotal;

  public AccountingReportItemV2Dto() {
  }

  public AccountingReportItemV2Dto(AccountingReportItemBuilder builder) {
    this.balances = builder.balances;
    this.churchFirstLeaderPercentageTotal = builder.churchFirstLeaderPercentageTotal;
    this.churchSecondLeaderPercentageTotal = builder.churchSecondLeaderPercentageTotal;
    this.mainLeaderPercentageTotal = builder.mainLeaderPercentageTotal;
    this.mainChurchPercentageTotal = builder.mainChurchPercentageTotal;
    this.ministryPercentageTotal = builder.ministryPercentageTotal;
    this.transferBalances = builder.transferBalances;
    this.transferGeolBalances = builder.transferGeolBalances;
    this.transferBalancesTotal = builder.transferBalancesTotal;
    this.transferGeolBalancesTotal = builder.transferGeolBalancesTotal;
    this.total = builder.total;
  }

  public List<AccountingReportItemDto> getBalances() {
    return balances;
  }

  public void setBalances(List<AccountingReportItemDto> balances) {
    this.balances = balances;
  }

  public List<AccountingReportItemDto> getTransferBalances() {
    return transferBalances;
  }

  public void setTransferBalances(List<AccountingReportItemDto> transferBalances) {
    this.transferBalances = transferBalances;
  }

  public List<AccountingReportItemDto> getTransferGeolBalances() {
    return transferGeolBalances;
  }

  public void setTransferGeolBalances(List<AccountingReportItemDto> transferGeolBalances) {
    this.transferGeolBalances = transferGeolBalances;
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

  public float getTransferBalancesTotal() {
    return transferBalancesTotal;
  }

  public void setTransferBalancesTotal(float transferBalanceTotal) {
    this.transferBalancesTotal = transferBalanceTotal;
  }

  public float getTransferGeolBalancesTotal() {
    return transferGeolBalancesTotal;
  }

  public void setTransferGeolBalancesTotal(float transferGeolBalanceTotal) {
    this.transferGeolBalancesTotal = transferGeolBalanceTotal;
  }

  public static class AccountingReportItemBuilder {
    private List<AccountingReportItemDto> balances;
    private float churchFirstLeaderPercentageTotal;
    private float churchSecondLeaderPercentageTotal;
    private float mainChurchPercentageTotal;
    private float ministryPercentageTotal;
    private float mainLeaderPercentageTotal;
    private List<AccountingReportItemDto> transferBalances;
    private List<AccountingReportItemDto> transferGeolBalances;
    private float total;
    private float transferBalancesTotal;
    private float transferGeolBalancesTotal;

    public AccountingReportItemBuilder() {
    }

    public AccountingReportItemBuilder balances(List<AccountingReportItemDto> balances) {
      this.balances = balances;
      return this;
    }

    public AccountingReportItemBuilder transferBalances(List<AccountingReportItemDto> transferBalances) {
      this.transferBalances = transferBalances;
      return this;
    }

    public AccountingReportItemBuilder transferGeolBalances(List<AccountingReportItemDto> transferGeolBalances) {
      this.transferGeolBalances = transferGeolBalances;
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

    public AccountingReportItemBuilder transferBalancesTotal(List<AccountingReportItemDto> balances) {
      float result = this.transferBalances
          .stream()
          .map(AccountingReportItemDto::getValue)
          .reduce(0f, Float::sum);

      this.transferBalancesTotal = result;

      return this;
    }

    public AccountingReportItemBuilder transferGeolBalancesTotal(List<AccountingReportItemDto> balances) {
      float result = this.transferGeolBalances
          .stream()
          .map(AccountingReportItemDto::getValue)
          .reduce(0f, Float::sum);

      this.transferGeolBalancesTotal = result;

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
