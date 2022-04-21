package com.tw.precharge.infrastructure.repository;

import com.tw.precharge.entity.RentUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author lexu
 */
@Repository
public interface RentUserRepository extends JpaRepository<RentUser,Integer> {

    Optional<RentUser> getUserById(Integer userId);

    Optional<RentUser> getRentUserByAccount(String account);

}
