package com.jojoldu.book.springboot.config;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jojoldu.book.springboot.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Configuration
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UsersService usersService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        // IP, 세션 ID
        WebAuthenticationDetails web = (WebAuthenticationDetails) authentication.getDetails();
        System.out.println("IP : " + web.getRemoteAddress());
        System.out.println("Session ID : " + web.getSessionId());

        // 인증 ID
        System.out.println("name : " + authentication.getName());

        // 권한 리스트
        List<GrantedAuthority> authList = (List<GrantedAuthority>) authentication.getAuthorities();
        System.out.print("권한 : ");
        for(int i = 0; i< authList.size(); i++) {
            System.out.print(authList.get(i).getAuthority() + " ");
        }
        if(request.getParameter("token") !=null){
            System.out.println((String) request.getParameter("token"));
            usersService.tokenUpdate(authentication.getName(),(String) request.getParameter("token"));
        }
        response.sendRedirect("/");
    }
}
