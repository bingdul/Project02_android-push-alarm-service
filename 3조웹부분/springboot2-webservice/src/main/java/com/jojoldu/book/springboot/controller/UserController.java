package com.jojoldu.book.springboot.controller;

import com.jojoldu.book.springboot.domain.users.Users;
import com.jojoldu.book.springboot.domain.users.UsersRepository;
import com.jojoldu.book.springboot.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
public class UserController
{
    private final UsersService userService;
    @Autowired
    private UsersRepository usersRepository;// 글 아래에서 생성할 예정
    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // 시큐리티에서 빈(Bean) 생성할 예정 /** * 인덱스 페이지 * *
 /** * 로그인 폼 페이지 * * @return */


    @RequestMapping("/loginForm")
    public String loginForm() {
        return "/loginForm";
    } /** * 회원 가입 페이지 * * @return */
    @RequestMapping("signupForm")
    public String joinForm() {
        return "signupForm";
    } /** * 회원 가입이 실행되는 부분 * *@param user * @return */
    @PostMapping("join")
    public String join(Model model,Users user) {
         if(!userService.checkDuplicate(user.getUsername())) {
             user.setRole("ROLE_ADMIN"); // 권한 정보는 임시로 ROLE_ADMIN으로 넣는다.
             user.setPassword(passwordEncoder.encode(user.getPassword()));
             usersRepository.save(user);
             return "redirect:/loginForm";
         }
         else{
             model.addAttribute("errormsg","중복된 계정입니다.");
             return "forward:/signupForm";
         }
    }

    @GetMapping("/machine")
    public String machine() {
        return "/machine";
    }

    @GetMapping("/chart")
    public String chart() {
    return "/chart";
}

}
