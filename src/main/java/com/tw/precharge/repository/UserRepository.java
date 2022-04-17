package com.tw.precharge.repository;

import com.tw.precharge.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author lexu
 */
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> getUserById(Integer userId);

}
