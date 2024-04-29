package co.istad.mobilebankingcstad.features.auth;


import co.istad.mobilebankingcstad.features.auth.dto.AuthRequest;
import co.istad.mobilebankingcstad.features.auth.dto.AuthResponse;
import co.istad.mobilebankingcstad.features.auth.dto.RefreshTokenRequest;
import co.istad.mobilebankingcstad.features.user.UserService;
import co.istad.mobilebankingcstad.features.user.dto.UserRequest;
import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import co.istad.mobilebankingcstad.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
@SecurityRequirements(value = {})
public class AuthRestController {

    private final AuthServiceImpl authService;
    private final UserService userService;

    @Operation(summary = "Login to get access token")
    @PostMapping("/login")
    public BaseResponse<AuthResponse> login(@RequestBody AuthRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.login(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "Refresh access token")
    public BaseResponse<AuthResponse> refresh(@RequestBody RefreshTokenRequest request) {
        return BaseResponse.<AuthResponse>ok()
                .setPayload(authService.refreshToken(request));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Register new user"
            , requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
            content = @Content(schema = @Schema(implementation = UserRequest.class),
                    examples = @ExampleObject(value = """
                            {
                              "username": "sokkhann",
                              "fullName": "string",
                              "gender": "male",
                              "pin": "898989",
                              "email": "sokkhann@gmail.com",
                              "password": "string",
                              "profileImage": "string",
                              "phoneNumber": "string",
                              "cityOrProvince": "string",
                              "khanOrDistrict": "string",
                              "sangkatOrCommune": "string",
                              "employeeType": "string",
                              "companyName": "string",
                              "mainSourceOfIncome": "string",
                              "monthlyIncomeRange": 0,
                              "studentCardId": "string",
                              "roles": [
                                "ADMIN","STUFF"
                              ]
                            }
                                                     
                                                        
                            """)

            )
    )
    )
    public BaseResponse<UserResponse> registerUser(
            @Valid @RequestBody UserRequest userRequest) {
        return BaseResponse.<UserResponse>createSuccess()
                .setPayload(userService.createUser(userRequest));
    }


}
