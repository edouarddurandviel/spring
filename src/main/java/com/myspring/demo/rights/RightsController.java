package com.myspring.demo.rights;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

import com.myspring.demo.rights.dto.RightsResponse;
import com.myspring.demo.rights.dto.RightsValidation;


@RestController
@RequestMapping("/api/rights")
public class RightsController {

    private final RightsService rightsService;

     public RightsController(
            RightsService rightsService
        ) {
        this.rightsService = rightsService;
    }


    @PostMapping("/create/{userId}/all")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RightsResponse> saveAllUserRight(
        @PathVariable Long userId, 
        @Valid @RequestBody RightsValidation request
    ) {
        
        RightsResponse user = rightsService.saveAllUserRight(userId, request);
        return ResponseEntity.ok().body(user);
    }

}