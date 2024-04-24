package co.istad.mobilebankingcstad.features.accounts;


import co.istad.mobilebankingcstad.domain.UserAccount;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import co.istad.mobilebankingcstad.features.accounttype.AccountTypeRepository;
import co.istad.mobilebankingcstad.features.user.UserRepository;
import co.istad.mobilebankingcstad.features.useraccount.UserAccountRepository;
import co.istad.mobilebankingcstad.mapper.AccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountMapper accountMapper;
    private final AccountTypeRepository accountTypeRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;

    @Override
    public List<AccountResponse> getAllAccounts() {
        return userAccountRepository.findAll()
                .stream()
                .map(accountMapper::mapUserAccountToAccountResponse)
                .toList();
    }

    @Override
    public AccountResponse createAccount(AccountRequest request) {
        // check account type
        var accountType = accountTypeRepository
                .findByName(request.accountType())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "Account Type : " + request.accountType() + " is not valid! "));
        var owner = userRepository.findById(request.userId())
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                "User ID = " + request.userId() + " is not a valid user"
                        )
                );
        if (userAccountRepository.countAccountsByUserId(request.userId()) >= 5) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "User ID = " + request.userId() + " already has 5 accounts"
            );
        }
        var account = accountMapper.mapRequestToEntity(request);
        account.setAccountType(accountType);
        // account info from the request
        UserAccount userAccount = new UserAccount()
                .setAccount(account)
                .setUser(owner)
                .setIsDisabled(false);
        userAccountRepository.save(userAccount);
        return accountMapper.mapUserAccountToAccountResponse(userAccount);
    }

    @Override
    public AccountResponse findAccountById(String id) {
        var userAccount = userAccountRepository.findByAccount_Id(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Account with id = " + id + " doesn't exist ! "));
        return accountMapper.mapUserAccountToAccountResponse(userAccount);
    }

    @Override
    public AccountResponse findAccountByAccountNumber(String accountNumber) {
        var accountByAccountNumber = userAccountRepository
                .findByAccount_AccountNumber(accountNumber)
                .orElseThrow(
                        () ->
                                new ResponseStatusException(
                                        HttpStatus.NOT_FOUND,
                                        "Account with Account Number = " + accountNumber +
                                                " doesn't exist !")
                );
        return accountMapper.mapUserAccountToAccountResponse(accountByAccountNumber);
    }

    @Override
    public List<AccountResponse> findAccountsByUserId(String userId) {
        var userAccountByUserId = userAccountRepository.findByUser_Id(userId);

        return userAccountByUserId.stream()
                .map(accountMapper::mapUserAccountToAccountResponse)
                .toList();

    }
}
