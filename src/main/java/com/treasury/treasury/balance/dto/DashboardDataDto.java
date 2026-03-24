package com.treasury.treasury.balance.dto;

public record DashboardDataDto(
    Float totalIncomings,
    Float totalOutgoings,
    Float totalBalance
) {}
