package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddBonusDTO {
    private Integer userId;
    /**
     * 加多少积分
     */
    private Integer bonus;

}
