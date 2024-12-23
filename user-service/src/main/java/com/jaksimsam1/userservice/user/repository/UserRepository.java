package com.jaksimsam1.userservice.user.repository;

import com.jaksimsam1.userservice.user.repository.jpa.UserJPARepository;
import com.jaksimsam1.userservice.user.repository.query.UserQueryRepository;

/**
 * Extends UserJPARepository and UserQueryRepository
 * <br><br>
 * UserJPARepository: Spring Data JPA Repository(extends JpaRepository) <br>
 * UserQueryRepository: QueryDSL custom Repository(implements)
 */
public interface UserRepository extends UserJPARepository, UserQueryRepository {
}
