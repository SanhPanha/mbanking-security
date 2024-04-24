package co.istad.mobilebankingcstad.features.auth.dto;


import lombok.Builder;

@Builder
public record AuthRequest (
        String email,
        String password
){
}
