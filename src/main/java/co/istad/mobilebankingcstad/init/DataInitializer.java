package co.istad.mobilebankingcstad.init;

import co.istad.mobilebankingcstad.domain.AccountType;
import co.istad.mobilebankingcstad.domain.Authority;
import co.istad.mobilebankingcstad.domain.Role;
import co.istad.mobilebankingcstad.features.accounttype.AccountTypeRepository;
import co.istad.mobilebankingcstad.features.authority.AuthorityRepository;
import co.istad.mobilebankingcstad.features.roles.RoleRepository;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.function.array.ArraySliceUnnestFunction;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


// populate database ( role with some data )
@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepository roleRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AuthorityRepository authorityRepository;
    @PostConstruct
    void initAuthority(){
        List<String> authorities = List.of("READ","WRITE","DELETE");
        if(authorityRepository.count()==0){
            authorities.forEach(auth -> {
                var authority = new Authority();
                authority.setName(auth);
                authorityRepository.save(authority);
            });
        }
    }

    @PostConstruct
    void roleInit(){
        List<String> roles = List.of("ADMIN","USER");
        if(roleRepository.count()==0){
        var allAuthorities =  new HashSet<>(authorityRepository.findAll());
            for(var role : roles){
                var roleObj = new Role();
                if(role.equals("ADMIN")){
                    roleObj.setAuthorities(allAuthorities);
                }else if (role.equals("USER")){
                    roleObj.setAuthorities(
                            allAuthorities.stream()
                            .filter(auth -> auth.getName().equals("READ"))
                            .collect(Collectors.toSet())
                    );
                }
                roleObj.setName(role);
                roleRepository.save(roleObj);
            }
        }

    }

    @PostConstruct
    void accountTypesInit(){
        List<AccountType> accountTypes = new ArrayList<>(){{
            add(new AccountType()
                    .setName("SAVINGS")
                    .setDescription("This is the type of account that you gain interest when you save your money in the bank"));

            add(new AccountType()
                    .setName("PAYROLLS")
                    .setDescription("This is account to get paid by company monthly."));
            add(new AccountType()
                    .setName("Card")
                    .setDescription("Allow you to create different card for personal uses!"));
        }};

        if(accountTypeRepository.findAll().isEmpty())
        {
            accountTypeRepository.saveAll(accountTypes);

        }
    }
}
