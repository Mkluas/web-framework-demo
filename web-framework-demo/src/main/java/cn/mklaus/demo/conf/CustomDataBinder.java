package cn.mklaus.demo.conf;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Mklaus
 * @date 2018-08-06 上午11:43
 */
@ControllerAdvice
public class CustomDataBinder {

    @ModelAttribute(value = "userId")
    public String userId() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        Object userIdAttribute = requestAttributes.getAttribute(WebUserInterceptor.SESSION_USER_ID_KEY, RequestAttributes.SCOPE_SESSION);
        return userIdAttribute == null ? null: userIdAttribute.toString();
    }

}
