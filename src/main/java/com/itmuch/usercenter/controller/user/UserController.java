package com.itmuch.usercenter.controller.user;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.itmuch.usercenter.auth.CheckLogin;
import com.itmuch.usercenter.domain.dto.user.JwtTokenRespDTO;
import com.itmuch.usercenter.domain.dto.user.LoginRespDTO;
import com.itmuch.usercenter.domain.dto.user.UserLoginDTO;
import com.itmuch.usercenter.domain.dto.user.UserRespDTO;
import com.itmuch.usercenter.domain.entity.user.User;
import com.itmuch.usercenter.service.user.UserService;
import com.itmuch.usercenter.util.JwtOperator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/users")
//lombok去除idea 的autowired的警告
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserController {

    private final UserService userService;
    private final WxMaService wxMaService;
    private final JwtOperator jwtOperator;

    @CheckLogin
    @GetMapping("{id}")
    public User findById(@PathVariable Integer id){
        log.info("被请求了...");
        return this.userService.findById(id);
    }

    /**
     * 模拟token生成
     */
    @GetMapping("/gen-token")
    public String genToken(){
        //颁发token
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("id",1);
        userInfo.put("wxNickName","王菲");
        userInfo.put("role","admin");
        return this.jwtOperator.generateToken(userInfo);
    }


    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody UserLoginDTO loginDTO) throws WxErrorException {
        //微信小程序服务端校验是否已经登录的结果
        WxMaJscode2SessionResult result = this.wxMaService.getUserService()
                .getSessionInfo(loginDTO.getCode());
        //微信的openId,用户在微信这边的唯一标识
        String openid = result.getOpenid();

        //看用户是否注册，如果没有注册就注册,已经注册就颁发token
        User user = this.userService.login(loginDTO, openid);

        //颁发token
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put("id",user.getId());
        userInfo.put("wxNickName",user.getWxNickname());
        userInfo.put("role",user.getRoles());
        String token = jwtOperator.generateToken(userInfo);

        log.info("用户{}登录成功，生成的token:{},有效期到:{}",
                loginDTO.getWxNickName(),
                token,
                jwtOperator.getExpirationTime());

        //构建响应
        return LoginRespDTO.builder()
               .user(
                       UserRespDTO.builder()
                               .id(user.getId())
                               .avatarUrl(user.getAvatarUrl())
                               .bonus(user.getBonus())
                               .wxNickName(user.getWxNickname())
                               .build()
               )
                .token(
                        JwtTokenRespDTO.builder()
                                .expireationTime(jwtOperator.getExpirationTime().getTime())
                                .token(token)
                                .build()
                ).build();

    }
}
