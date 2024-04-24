package co.istad.mobilebankingcstad.features.user;

import co.istad.mobilebankingcstad.domain.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isDeleted = :status WHERE u.id = :userId")
    int updateDeletedStatusById(String userId, boolean status);


    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.isBlocked = :status WHERE u.id = :userId")
    int updateBlockedStatusById(String userId, boolean status);

    Optional<User> findByEmail(String email);
}
