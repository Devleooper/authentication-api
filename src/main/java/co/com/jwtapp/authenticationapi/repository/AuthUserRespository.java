package co.com.jwtapp.authenticationapi.repository;

import co.com.jwtapp.authenticationapi.entities.AuthUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AuthUserRespository extends CrudRepository<AuthUser, Long> {

    AuthUser findByName(String name);

    boolean existsByName(String name);

    AuthUser findByNameAndPassword(String name, String password);

    AuthUser findByUid(UUID uuid);
}
