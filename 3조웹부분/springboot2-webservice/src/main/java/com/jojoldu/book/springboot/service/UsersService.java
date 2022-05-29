package com.jojoldu.book.springboot.service;

import com.jojoldu.book.springboot.domain.users.Users;
import com.jojoldu.book.springboot.domain.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UsersService {
    private final UsersRepository usersRepository;

    @Transactional
    public void tokenUpdate(String usrname, String fcmtoken){
        Users users=usersRepository.findByUsername(usrname).orElseThrow(()->new IllegalArgumentException("해당 유저가 없습니다."));
        users.fcmTokenUpdate(fcmtoken);
    }
    @Transactional(readOnly = true)
    public List<Users> findAllDesc(){
        return usersRepository.findAllDesc();
    }
    public boolean checkDuplicate(String username){
        return usersRepository.existsByUsername(username);
    }
}
