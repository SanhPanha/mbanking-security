package co.istad.mobilebankingcstad.features.user.dto;


import jakarta.validation.constraints.*;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Builder
public record UserRequest(
        @NotEmpty
        @NotNull
        String username,

        @NotEmpty
        String fullName,
        @NotEmpty
        String gender,


// handle this properly next time
        @Size(max = 6, min = 6, message = "Pin can only be 6 digit")
        @Pattern(regexp = "\\d+", message = "Pin can only be 6 digit")
        String pin,
        @Email(message = "Email format is not correct!")
        String email,
        String password,
        String profileImage,
        String phoneNumber,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        String studentCardId,
        Set<String> roles
) {
}
