package com.jojoldu.book.springboot.web;



import com.jojoldu.book.springboot.domain.users.Users;
import com.jojoldu.book.springboot.service.FirebaseCloudMessageService;
import com.jojoldu.book.springboot.service.UsersService;
import com.jojoldu.book.springboot.service.posts.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import com.jojoldu.book.springboot.web.dto.PostsSaveRequestDto;
import com.jojoldu.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostApiController {
    private final UsersService usersService;
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) throws IOException {
        //알림 기능 추가
        List<Users> users= usersService.findAllDesc();
        for (Users u:users ) {
            System.out.println(u.getFcmtoken());
            if(u.getFcmtoken()!=null)
                firebaseCloudMessageService.sendMessageTo(u.getFcmtoken(),"공지사항",requestDto.getTitle());
        }
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto){
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id)
    {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id){
        postsService.delete(id);
        return id;
    }





}
