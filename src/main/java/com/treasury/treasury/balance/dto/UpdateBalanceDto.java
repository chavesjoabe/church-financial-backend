package com.treasury.treasury.balance.dto;

import com.treasury.treasury.balance.constants.BalanceIncomingTypes;
import com.treasury.treasury.balance.constants.BalanceStatus;

public record UpdateBalanceDto(
    BalanceStatus status,
    BalanceIncomingTypes incomingType,
    String category,
    String description) {
}
