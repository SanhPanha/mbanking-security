package co.istad.mobilebankingcstad.features.user.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record UserUpdateRequest(
        String fullName,
        String gender,
        String profileImage,
        String phoneNumber,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        String studentCardId

) {
}
