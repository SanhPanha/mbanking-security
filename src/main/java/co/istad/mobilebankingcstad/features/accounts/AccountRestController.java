package co.istad.mobilebankingcstad.features.accounts;

import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import co.istad.mobilebankingcstad.utils.BaseResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountRestController {
    private final AccountService accountService;


    //    get all will need to do with the pagination !
    @GetMapping
    @Operation(summary = "Get all accounts !")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<List<AccountResponse>> getAllAccounts() {
        return BaseResponse.<List<AccountResponse>>ok()
                .setPayload(accountService.getAllAccounts());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create account !")
    // default request body for the swagger
    public BaseResponse<AccountResponse> createAccount(
            @RequestBody AccountRequest request
    ) {
        return BaseResponse.<AccountResponse>createSuccess()
                .setPayload(accountService.createAccount(request));
    }

    // get account by account id


    //    @Parameters
    @GetMapping("/{id}")
    @Operation(summary = "Get account by accountId")
    public BaseResponse<AccountResponse> getAccountByAccountId(
            @Parameter(
                    description = "Account ID ",
                    required = true,
                    example = "e55eb02b-0537-4432-835b-1af26d4b5906"
            )
            @PathVariable String id) {

        return BaseResponse.<AccountResponse>ok()
                .setPayload(accountService.findAccountById(id));
    }

    @GetMapping("/account-number/{id}")
    @Operation(summary = "Find Accounts By Account Number ")
    public BaseResponse<AccountResponse> getAccountByAccountNumber(
            @Parameter(
                    description = "account number",
                    required = true,
                    example = "string1"
            )
            @PathVariable("id") String accountNumber){
        return BaseResponse.<AccountResponse>
                ok()
                .setPayload(accountService.findAccountByAccountNumber(accountNumber));
    }


    @GetMapping("/user/{id}")
    @Operation(summary = "find account(s) by user id ")
    public BaseResponse<List<AccountResponse>> findAccountsByUserId(
            @Parameter(
                    description = "user id ",
                    required = true,
                    example = "bb455eff-41ab-41aa-8e9a-dd1f0a3c69a5"
            )
            @PathVariable String id){
        return BaseResponse.<List<AccountResponse>>ok()
                .setPayload(accountService.findAccountsByUserId(id))
                ;
    }


}
