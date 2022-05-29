package com.jojoldu.book.springboot.web.dto;

import com.jojoldu.book.springboot.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;

@RequiredArgsConstructor
@Controller
public class BoardController {


    private final PostsService postsService;

    @GetMapping("/board")
    public String index(Model model) {

        model.addAttribute("posts", postsService.findAllDesc());
        return "board";
    }

    @GetMapping("/board/{id}")
    public String boardpost(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "boardposts";
    }
    @GetMapping("/board/posts/save")
    public String postsSave(Model model, Principal principal){
        if (principal !=null)
            model.addAttribute("usrname",principal.getName());

        return "posts-save";
    }


    @GetMapping("/board/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model,Principal principal){
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        if(principal.getName().equals(dto.getAuthor()))
        {
            return "posts-update";
        }
        else
            return "redirect:/board";
    }




}
