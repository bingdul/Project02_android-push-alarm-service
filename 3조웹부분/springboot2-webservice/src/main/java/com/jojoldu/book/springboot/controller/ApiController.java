package com.jojoldu.book.springboot.controller;

import com.jojoldu.book.springboot.service.FirebaseCloudMessageService;
import com.jojoldu.book.springboot.service.UsersService;
import com.jojoldu.book.springboot.dto.tokens.TokensSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ApiController {
    private final UsersService usersService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/token/update")
    public void tokenSave(@RequestBody TokensSaveRequestDto requestDto){
            System.out.println("username:"+requestDto.getUsername()+", token:" + requestDto.getToken());
            usersService.tokenUpdate(requestDto.getUsername(), requestDto.getToken());
        }
    }

