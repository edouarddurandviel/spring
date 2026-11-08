package com.myspring.demo.user.utils;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

public class CookiesUtils {

    public static void clearCookies(HttpServletRequest request, String ...cookies) {
        Assert.notNull(cookies, "List of cookies cannot be null");
		for (String cookieName : cookies) {
				Cookie cookie = new Cookie(cookieName, null);
				String contextPath = request.getContextPath();
				String cookiePath = StringUtils.hasText(contextPath) ? contextPath : "/";
				cookie.setPath(cookiePath);
				cookie.setMaxAge(0);
				cookie.setSecure(request.isSecure());
			};
		}
}
