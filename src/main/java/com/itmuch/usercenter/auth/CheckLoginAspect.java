package com.itmuch.usercenter.auth;

import com.itmuch.usercenter.util.JwtOperator;
import io.jsonwebtoken.Claims;
import lombok.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckLoginAspect {

    private final JwtOperator jwtOperator;

    //所有加了CheckLogin注解的方法都会走到这里
    @Around("@annotation(com.itmuch.usercenter.auth.CheckLogin)")
    public Object checkLogin(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            //1、从header中获取token
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
            HttpServletRequest request = attributes.getRequest();

            String token = request.getHeader("x-token");
            //2、校验token是否合法
            Boolean isValid = this.jwtOperator.validateToken(token);
            if (!isValid){
                throw new SecurityException("token不合法!");
            }

            //3、校验成功，将用户的信息设置到request的attribute里面
            Claims claims = jwtOperator.getClaimsFromToken(token);
            request.setAttribute("id",claims.get("id"));
            request.setAttribute("wxNickName",claims.get("wxNickName"));
            request.setAttribute("role",claims.get("role"));

            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new SecurityException("token不合法!");
        }

    }
}












