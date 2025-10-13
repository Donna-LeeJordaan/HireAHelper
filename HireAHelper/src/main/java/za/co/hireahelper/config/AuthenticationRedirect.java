package za.co.hireahelper.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class AuthenticationRedirect implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_CLIENT")) {
            response.sendRedirect("http://localhost:5173/client/dashboard");
        } else if (roles.contains("ROLE_SERVICE_PROVIDER")) {
            response.sendRedirect("http://localhost:5173/serviceProvider/dashboard");
        } else if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("http://localhost:5173/admin/dashboard");
        } else {
            response.sendRedirect("/");
        }
    }
}