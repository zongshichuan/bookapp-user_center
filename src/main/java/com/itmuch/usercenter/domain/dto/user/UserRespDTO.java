package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserRespDTO {
    private Integer id;
    /**
     * 头像地址
     */
    private String avatarUrl;
    /**
     *积分
     */
    private Integer bonus;
    /**
     * 微信昵称
     */
    private String wxNickName;
}
