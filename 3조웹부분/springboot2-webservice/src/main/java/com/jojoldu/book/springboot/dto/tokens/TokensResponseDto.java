package com.jojoldu.book.springboot.dto.tokens;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokensResponseDto {

    private String token;
    @Builder
    public TokensResponseDto(String token)
    {
        this.token=token;
    }
}