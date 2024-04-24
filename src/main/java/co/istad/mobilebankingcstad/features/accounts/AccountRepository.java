package co.istad.mobilebankingcstad.features.accounts;

import co.istad.mobilebankingcstad.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,String> {
}
