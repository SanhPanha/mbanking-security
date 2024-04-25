package co.istad.mobilebankingcstad.features.user;


import co.istad.mobilebankingcstad.features.user.dto.UserRequest;
import co.istad.mobilebankingcstad.features.user.dto.UserResponse;
import co.istad.mobilebankingcstad.features.user.dto.UserUpdateRequest;
import co.istad.mobilebankingcstad.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.representer.BaseRepresenter;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor

//provide default values for requestbody and params
public class UserRestController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Get all users")
    public BaseResponse<List<UserResponse>> getAllUser() {
        return BaseResponse.<List<UserResponse>>ok()
                .setPayload(userService.getAllUsers());

    }


    @GetMapping("/{id}")
    @Operation(summary = "Get user by id")
    public BaseResponse<UserResponse> getUserById(@PathVariable String id) {
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.getUserById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse<?> deleteUserById(@PathVariable String id) {
        userService.deleteUserById(id);
        return BaseResponse.ok();
    }


    @PatchMapping("/{id}")
    @Operation(summary = "Update user by id")
    // configure swagger to provide the default request body for updating the user
    public BaseResponse<UserResponse> updateUserByID(
            @PathVariable() String id,@RequestBody UserUpdateRequest userRequest){
        return BaseResponse.<UserResponse>updateSuccess()
                .setPayload(userService.updateUserById(id,userRequest));
    }


    @PatchMapping("/{id}/disable")
    public BaseResponse<UserResponse> disableUser(@PathVariable() String id){
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.disableUser(id));
    }
    @PatchMapping("/{id}/enable")
    public BaseResponse<UserResponse> enableUser(@PathVariable String id){
        return BaseResponse.<UserResponse>ok()
                .setPayload(userService.enableUser(id));
    }

}
