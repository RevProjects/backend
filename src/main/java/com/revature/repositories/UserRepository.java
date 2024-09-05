package com.revature.repositories;

import com.revature.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {


    Optional<Users> findUsersByName(String name);
    Optional<Users> findUsersByNameAndPassword(String username, String password);
    Optional<Users> findUsersById(Long userid);
    Optional<Users> findGamesById(Long userid);



}
