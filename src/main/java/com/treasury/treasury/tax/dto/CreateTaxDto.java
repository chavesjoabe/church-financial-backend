package com.treasury.treasury.tax.dto;

public class CreateTaxDto {
  private Double firstLeaderPercentage;
  private Double secondLeaderPercentage;
  private Double mainChurchPercentage;
  private Double ministryPercentage;
  private Double mainLeaderPercentage;
  private Double transferMainLeaderPercentage;
  private Double transferMainChurchPercentage;

  public CreateTaxDto() {
  }

  public CreateTaxDto(
      Double firstLeaderPercentage,
      Double secondLeaderPercentage,
      Double mainChurchPercentage,
      Double ministryPercentage,
      Double mainLeaderPercentage,
      Double transferMainLeaderPercentage,
      Double transferMainChurchPercentage) {
    this.firstLeaderPercentage = firstLeaderPercentage;
    this.secondLeaderPercentage = secondLeaderPercentage;
    this.mainChurchPercentage = mainChurchPercentage;
    this.ministryPercentage = ministryPercentage;
    this.mainLeaderPercentage = mainLeaderPercentage;
    this.transferMainLeaderPercentage = transferMainLeaderPercentage;
    this.transferMainChurchPercentage = transferMainChurchPercentage;
  }

  public Double getFirstLeaderPercentage() {
    return firstLeaderPercentage;
  }

  public void setFirstLeaderPercentage(Double firstLeaderPercentage) {
    this.firstLeaderPercentage = firstLeaderPercentage;
  }

  public Double getSecondLeaderPercentage() {
    return secondLeaderPercentage;
  }

  public void setSecondLeaderPercentage(Double firstLeaderPercentage) {
    this.secondLeaderPercentage = firstLeaderPercentage;
  }

  public Double getMainChurchPercentage() {
    return mainChurchPercentage;
  }

  public void setMainChurchPercentage(Double mainChurchPercentage) {
    this.mainChurchPercentage = mainChurchPercentage;
  }

  public Double getMinistryPercentage() {
    return ministryPercentage;
  }

  public void setMinistryPercentage(Double ministryPercentage) {
    this.ministryPercentage = ministryPercentage;
  }

  public Double getMainLeaderPercentage() {
    return mainLeaderPercentage;
  }

  public void setMainLeaderPercentage(Double mainLeaderPercentage) {
    this.mainLeaderPercentage = mainLeaderPercentage;
  }

  public Double getTransferMainLeaderPercentage() {
    return transferMainLeaderPercentage;
  }

  public void setTransferMainLeaderPercentage(Double transferMainLeaderPercentage) {
    this.transferMainLeaderPercentage = transferMainLeaderPercentage;
  }

  public Double getTransferMainChurchPercentage() {
    return transferMainChurchPercentage;
  }

  public void setTransferMainChurchPercentage(Double transferMainChurchPercentage) {
    this.transferMainChurchPercentage = transferMainChurchPercentage;
  }
}
