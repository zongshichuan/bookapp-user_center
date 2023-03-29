package com.itmuch.usercenter.domain.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JwtTokenRespDTO {
    /**
     * token
     */
    private String token;
    /**
     * 过期时间
     */
    private Long expireationTime;
}
