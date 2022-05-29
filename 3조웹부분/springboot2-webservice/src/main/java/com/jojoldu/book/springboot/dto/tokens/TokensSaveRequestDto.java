package com.jojoldu.book.springboot.dto.tokens;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokensSaveRequestDto {
    private String username;
    private String token;
    @Builder
    public TokensSaveRequestDto(String username,String token)
    {
        this.username=username;
        this.token=token;
    }
}
