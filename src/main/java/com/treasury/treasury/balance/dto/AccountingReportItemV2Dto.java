package com.treasury.treasury.balance.dto;

import java.util.List;

import com.treasury.treasury.balance.schema.Balance;

public class AccountingReportItemV2Dto {
  private List<AccountingReportItemDto> balances;
  private List<AccountingReportItemDto> transferBalances;
  private List<AccountingReportItemDto> transferGeolBalances;
  private List<Balance> nonOficialBalances;
  private TotalDto transferGeolBalancesTotal;
  private TotalDto balancesTotal;
  private TotalDto transferBalancesTotal;

  public AccountingReportItemV2Dto() {
  }

  public AccountingReportItemV2Dto(AccountingReportItemBuilder builder) {
    this.balances = builder.balances;
    this.transferBalances = builder.transferBalances;
    this.transferGeolBalances = builder.transferGeolBalances;
    this.nonOficialBalances = builder.nonOficialBalances;
    this.transferBalancesTotal = builder.transferBalancesTotal;
    this.transferGeolBalancesTotal = builder.transferGeolBalancesTotal;
    this.balancesTotal = builder.balancesTotal;
    this.transferBalancesTotal = builder.transferBalancesTotal;
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

  public void setNonOficialBalances(List<Balance> nonOficialBalances) {
    this.nonOficialBalances = nonOficialBalances;
  }

  public List<Balance> getNonOficialBalances() {
    return nonOficialBalances;
  }

  public TotalDto getTransferBalancesTotal() {
    return transferBalancesTotal;
  }

  public void setTransferBalancesTotal(TotalDto transferBalanceTotal) {
    this.transferBalancesTotal = transferBalanceTotal;
  }

  public TotalDto getTransferGeolBalancesTotal() {
    return transferGeolBalancesTotal;
  }

  public void setTransferGeolBalancesTotal(TotalDto transferGeolBalanceTotal) {
    this.transferGeolBalancesTotal = transferGeolBalanceTotal;
  }

  public TotalDto getBalancesTotal() {
    return balancesTotal;
  }

  public void setBalancesTotal(TotalDto balancesTotal) {
    this.balancesTotal = balancesTotal;
  }

  public static class AccountingReportItemBuilder {
    private List<AccountingReportItemDto> balances;
    private List<AccountingReportItemDto> transferBalances;
    private List<AccountingReportItemDto> transferGeolBalances;
    private List<Balance> nonOficialBalances;
    private TotalDto transferBalancesTotal;
    private TotalDto transferGeolBalancesTotal;
    private TotalDto balancesTotal;

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

    public AccountingReportItemBuilder nonOficialBalances(List<Balance> nonOficialBalances) {
      this.nonOficialBalances = nonOficialBalances;
      return this;
    }

    public AccountingReportItemBuilder transferGeolBalancesTotal(List<AccountingReportItemDto> balances) {
      this.transferGeolBalancesTotal = generateTotal(balances);

      return this;
    }

    public AccountingReportItemBuilder balancesTotal(List<AccountingReportItemDto> balances) {
      this.balancesTotal = generateTotal(balances);

      return this;
    }

    public AccountingReportItemBuilder transferBalancesTotal(List<AccountingReportItemDto> balances) {
      this.transferBalancesTotal = generateTotal(balances);

      return this;
    }

    private TotalDto generateTotal(List<AccountingReportItemDto> balances) {
      float churchFirstLeaderPercentageTotal = balances
          .stream()
          .map(AccountingReportItemDto::getChurchFirstLeaderPercentage)
          .reduce(0f, Float::sum);

      float churchSecondLeaderPercentageTotal = balances
          .stream()
          .map(AccountingReportItemDto::getChurchSecondLeaderPercentage)
          .reduce(0f, Float::sum);

      float mainChurchPercentageTotal = balances
          .stream()
          .map(AccountingReportItemDto::getMainChurchPercentage)
          .reduce(0f, Float::sum);

      float mainLeaderPercentageTotal = balances
          .stream()
          .map(AccountingReportItemDto::getMainLeaderPercentage)
          .reduce(0f, Float::sum);

      float ministryPercentageTotal = balances
          .stream()
          .map(AccountingReportItemDto::getMinistryPercentage)
          .reduce(0f, Float::sum);

      float total = balances
          .stream()
          .map(AccountingReportItemDto::getValue)
          .reduce(0f, Float::sum);

      return this.balancesTotal = new TotalDto(
          churchFirstLeaderPercentageTotal,
          churchSecondLeaderPercentageTotal,
          mainChurchPercentageTotal,
          mainLeaderPercentageTotal,
          ministryPercentageTotal,
          total);
    }

    public AccountingReportItemV2Dto build() {
      return new AccountingReportItemV2Dto(this);
    }
  }

  public static AccountingReportItemBuilder builder() {
    return new AccountingReportItemBuilder();
  }
}
