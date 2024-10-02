package com.jaksimsam1.userservice.user.repository;

import com.jaksimsam1.userservice.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

    User findByUserId(@Param("userId") String userId);

}
