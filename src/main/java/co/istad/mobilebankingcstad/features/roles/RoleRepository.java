package co.istad.mobilebankingcstad.features.roles;


import co.istad.mobilebankingcstad.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository  extends JpaRepository<Role,String> {

    Optional<Role> findByName(String name   );
}
