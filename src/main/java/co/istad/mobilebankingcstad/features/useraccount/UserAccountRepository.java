package co.istad.mobilebankingcstad.features.useraccount;

import co.istad.mobilebankingcstad.domain.User;
import co.istad.mobilebankingcstad.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, String> {


    // find useraccount by it's fields account -> account id
    Optional<UserAccount> findByAccount_Id(String id);

    Optional<UserAccount> findByAccount_AccountNumber(String accountNumber);
    List<UserAccount> findByUser_Id(String id);


    // method for counting the number of account by user id
    @Query("SELECT COUNT(ua) FROM user_accounts_tbl ua WHERE ua.user.id = ?1")
    int countAccountsByUserId(@Param("userId") String userId);

    //    using derived query instead
    Long countByUser(User user); // derived query

}
