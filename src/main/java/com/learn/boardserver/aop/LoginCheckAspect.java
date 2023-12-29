package com.learn.boardserver.aop;

import com.learn.boardserver.utils.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Log4j2
@Component
@Order(Ordered.LOWEST_PRECEDENCE)
@Aspect
public class LoginCheckAspect {

    /**
        ProceedingJoinPoint: AOP에서 포인트컷(Pointcut)으로 지정된 시점에서의 메서드 실행을 나타내는 객체
        Joinpoint: 포인트컷에서 선택된 메서드 실행 지점
     */
    @Around("@annotation(com.learn.boardserver.aop.LoginCheck) && @annotation(loginCheck)")
    public Object adminLoginCheck(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable{
        HttpSession session = (HttpSession) ((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest().getSession();

        // 컨트롤러에 넘겨 줄 값
        String id = null;
        int idIndex = 0;

        String userType = loginCheck.type().toString();
        switch (userType) {
            case "ADMIN" -> id = SessionUtil.getLoginAdminId(session);
            case "USER" -> id = SessionUtil.getLoginMemberId(session);
        }

        if(id == null) {
            log.error(joinPoint.toString() + "accountName : " + id);
            throw new HttpStatusCodeException(HttpStatus.UNAUTHORIZED, "로그인한 ID값을 확인해주세요") {};
        }
        
        // 정상적으로 로그인 한 상태

        Object[] modifiedArgs = joinPoint.getArgs();
        if(joinPoint != null) {
            modifiedArgs[idIndex] = id;
        }

        return joinPoint.proceed(modifiedArgs);
    }
}
