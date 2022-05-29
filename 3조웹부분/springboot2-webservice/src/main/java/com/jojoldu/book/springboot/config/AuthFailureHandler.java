package com.jojoldu.book.springboot.config;

import com.jojoldu.book.springboot.domain.users.Users;
import com.jojoldu.book.springboot.service.FirebaseCloudMessageService;
import com.jojoldu.book.springboot.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class AuthFailureHandler implements AuthenticationFailureHandler {
    private final UsersService usersService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String username = request.getParameter("username");        // request에서 getParameter를 사용하여 "username"에 대한 정보를 가져올 수 있다.
        // 로그인 실패 시 처리할 내용을 작성하여 확장할 수 있다.
        List<Users> users= usersService.findAllDesc();
        for (Users u:users ) {
            System.out.println("onFailure");
            if(u.getFcmtoken()!=null)
            firebaseCloudMessageService.sendMessageTo(u.getFcmtoken(),"LoginError",username);
        }
        if (exception instanceof AuthenticationServiceException) {
            request.setAttribute("loginFailMsg", "존재하지 않는 사용자입니다.");

        } else if(exception instanceof BadCredentialsException) {
            request.setAttribute("loginFailMsg", "아이디 또는 비밀번호가 틀립니다.");

        } else if(exception instanceof LockedException) {
            request.setAttribute("loginFailMsg", "잠긴 계정입니다..");

        } else if(exception instanceof DisabledException) {
            request.setAttribute("loginFailMsg", "비활성화된 계정입니다..");

        } else if(exception instanceof AccountExpiredException) {
            request.setAttribute("loginFailMsg", "만료된 계정입니다..");

        } else if(exception instanceof CredentialsExpiredException) {
            request.setAttribute("loginFailMsg", "비밀번호가 만료되었습니다.");
        }

        // 로그인 페이지로 다시 포워딩
        RequestDispatcher dispatcher = request.getRequestDispatcher("/loginForm");
        dispatcher.forward(request, response);
//        response.sendRedirect("/loginForm?error");     // 응답으로 리다이렉트를 보낸다.
        // 이 예제에서는 간단히 failLogin.html로 리다이렉션을 해주었다.

    }

}