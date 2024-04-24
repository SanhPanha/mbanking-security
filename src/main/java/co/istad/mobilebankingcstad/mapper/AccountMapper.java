package co.istad.mobilebankingcstad.mapper;
import co.istad.mobilebankingcstad.domain.Account;
import co.istad.mobilebankingcstad.domain.UserAccount;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountRequest;
import co.istad.mobilebankingcstad.features.accounts.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface AccountMapper {
    @Mapping(target = "accountType", ignore = true)
    Account mapRequestToEntity(AccountRequest accountRequest);
    @Mapping(target="id", source = "userAccount.account.id")
    @Mapping(target = "accountNumber", source = "userAccount.account.accountNumber")
    @Mapping(target = "accountName", source = "userAccount.account.accountName")
    @Mapping(target = "accountBalance", source = "userAccount.account.accountBalance")
    @Mapping(target = "user", source = "userAccount.user", qualifiedByName = "toUserResponse")
    @Mapping(target = "accountType", source = "userAccount.account.accountType.name")
    AccountResponse mapUserAccountToAccountResponse(UserAccount userAccount);

    //    @Mapping(target = "user", source = "user")
//    @Mapping(target = "user", source = "user", qualifiedByName = "toUserResponse")
//    void setUserForAccountResponse(@MappingTarget AccountResponse accountResponse, User user);


}
