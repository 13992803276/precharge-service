package com.tw.precharge.repositoryTest;

import com.tw.precharge.domain.user.RentUser;
import com.tw.precharge.infrastructure.repository.RentUserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class RentUserRepositoryTest {

    @Autowired
    private RentUserRepository rentUserRepository;

    @Test
    public void test_getUserById() {
        RentUser userById = rentUserRepository.getUserById(3).get();
        Assertions.assertEquals(userById.getName(),"张三");
        Assertions.assertEquals(userById.getAccount(),"zhangsan");
    }

    @Test
    public void test_save_rentUser() {
        RentUser user = RentUser.builder()
                .id(5)
                .name("张三丰")
                .account("zhangsanfeng")
                .address("北京市朝阳区")
                .balance(new BigDecimal(100))
                .phone("13992809270")
                .status("1")
                .created(LocalDate.now())
                .build();
        RentUser saveUser = rentUserRepository.save(user);
        Assertions.assertEquals(saveUser.getId(), 5);
        Assertions.assertEquals(saveUser.getName(), "张三丰");
        Assertions.assertEquals(saveUser.getAccount(), "zhangsanfeng");
    }


}