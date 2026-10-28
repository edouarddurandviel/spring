package com.myspring.demo.token;

import java.util.List;
import org.springframework.stereotype.Service;

import com.myspring.demo.token.dto.Token;
import com.myspring.demo.token.dto.TokenResponse;


@Service
public class TokenService {

    private final TokensRepository repository;

    public TokenService(TokensRepository repository){
        this.repository = repository;
    }


    public TokenResponse getOneToken(Long id) {
        Tokens Token = repository.findById(id).orElseThrow(
            () -> new RuntimeException("your Token not found")
        );
        return new TokenResponse(
            Token.getTokenId(),
            Token.getToken()
        );
    }

     public TokenResponse saveOneToken(Token values) {

        Tokens token = new Tokens();
       
        token.setToken(values.token());
        token.setTokenUser_Id(values.User_Id());

        Tokens Token = repository.save(token);
        
        return new TokenResponse(
            Token.getTokenId(),
            Token.getToken()
        );
    }

    public List<TokenResponse> getAllTokens() {
        List<TokenResponse> Tokens = repository.findAll()
        .stream()
        .map((Token) -> new TokenResponse( 
                    Token.getTokenId(),
                    Token.getToken()
        )).toList();
        return Tokens;
    }


    public TokenResponse deleteOneToken(Long id) {
        Tokens Token = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Token not found")
        );

        repository.delete(Token);

        return new TokenResponse(
            Token.getTokenId(),
            Token.getToken()
        );
    }
}