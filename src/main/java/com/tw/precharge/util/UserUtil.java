package com.tw.precharge.util;

import com.tw.precharge.constant.UserStatus;
import com.tw.precharge.entity.User;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author lexu
 */
public class UserUtil {

    private static final List<User> USER_LIST = Collections.unmodifiableList(
            Arrays.asList(
                    User.builder()
                            .id(1)
                            .name("徐乐")
                            .account("wuhen057")
                            .address("xian")
                            .balance(new BigDecimal(0))
                            .phone("13992809270")
                            .status("0")
                            .created(LocalDate.now())
                            .build(),
                    User.builder()
                            .id(1)
                            .name("张三")
                            .account("057")
                            .address("xian")
                            .balance(new BigDecimal(0))
                            .phone("17747389938")
                            .status("0")
                            .created(LocalDate.now())
                            .build(),
                    User.builder()
                            .id(1)
                            .name("李四")
                            .account("05799")
                            .address("xian")
                            .balance(new BigDecimal(0))
                            .phone("13938849940")
                            .status("1")
                            .created(LocalDate.now())
                            .build()
            )
    );

    public static User getUser() {
        return User.builder()
                .id(1)
                .name("徐乐")
                .account("wuhen057")
                .address("xian")
                .balance(new BigDecimal(0))
                .phone("13992809270")
                .status("1")
                .created(LocalDate.now())
                .build();
    }

    public static Optional <User> getUserById(Integer userId) {
        return Optional.ofNullable(USER_LIST.stream()
                .filter(user -> user.getId().equals(userId)
                        && user.getStatus().equals(UserStatus.NORMAL.getCode()))
                .collect(Collectors.toList())
                .get(0));
    }
}
