package co.istad.mobilebankingcstad.features.auth.dto;


import lombok.Builder;

@Builder
public record RefreshTokenRequest(
        String refreshToken
) {
}
