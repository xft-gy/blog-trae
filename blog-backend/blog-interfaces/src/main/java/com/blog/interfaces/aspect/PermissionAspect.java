package com.blog.interfaces.aspect;

import com.blog.common.annotation.RequireAdmin;
import com.blog.common.annotation.RequireLogin;
import com.blog.common.constant.CommonConstants;
import com.blog.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class PermissionAspect {

    @Before("@annotation(requireLogin)")
    public void checkLogin(JoinPoint joinPoint, RequireLogin requireLogin) {
        HttpServletRequest request = getRequest();
        Long userId = (Long) request.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            throw new BusinessException("Please login first");
        }
    }

    @Before("@annotation(requireAdmin)")
    public void checkAdmin(JoinPoint joinPoint, RequireAdmin requireAdmin) {
        HttpServletRequest request = getRequest();
        Long userId = (Long) request.getAttribute(CommonConstants.USER_ID_KEY);
        if (userId == null) {
            throw new BusinessException("Please login first");
        }
        
        String role = (String) request.getAttribute(CommonConstants.ROLE_KEY);
        if (!CommonConstants.UserRole.ADMIN.equals(role)) {
            throw new BusinessException("Admin permission required");
        }
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new BusinessException("Request not found");
        }
        return attributes.getRequest();
    }
}
