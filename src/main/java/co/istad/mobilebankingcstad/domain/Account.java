package co.istad.mobilebankingcstad.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Set;

@Entity(name = "accounts_tbl")
@Data
@Accessors(chain = true)
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String accountNumber;
    private String accountName;
    private BigDecimal accountBalance;
    @ManyToOne
    @JoinColumn(name = "account_type_id")
    private AccountType accountType;
    @OneToMany(mappedBy = "account")
    Set<UserAccount> userAccounts;

}
