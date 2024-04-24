package co.istad.mobilebankingcstad.features.accounts.dto;

import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        String id,
        String accountNumber,
        String accountName,
        BigDecimal accountBalance,
        UserResponse user,
        String accountType
) {
}
