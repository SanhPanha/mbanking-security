package co.istad.mobilebankingcstad.features.user.dto;


import lombok.Builder;

import java.util.Set;

@Builder
public record UserResponse(
        String id,
        String username,
        String fullName,
        String gender,
        String email,
        String phoneNumber,
        String profileImage,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String companyName,
        String mainSourceOfIncome,
        String monthlyIncomeRange,
        String studentCardId,
        Set<String> roles

        ) {
}
