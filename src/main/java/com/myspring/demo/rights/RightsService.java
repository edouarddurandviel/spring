package com.myspring.demo.rights;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring.demo.rights.dto.RightsResponse;
import com.myspring.demo.rights.dto.RightsValidation;



@Service
public class RightsService {

    private final RightsRepository repository;
     private final ObjectMapper objectMapper;

    public RightsService(RightsRepository repository,  ObjectMapper objectMapper){
        this.repository = repository;
        this.objectMapper = objectMapper;
    }


     public RightsResponse saveAllUserRight(Long UserId, RightsValidation content) {

        Rights rights = repository.findWithUserId(UserId).orElseThrow(
            () -> new RuntimeException("Right not found")
        );

        // convert to json
        JsonNode inputValues = objectMapper.valueToTree(content);

        // set content and save
        rights.setRightsContent(inputValues);
        Rights savedRight = repository.save(rights);


        
        RightsResponse result = objectMapper.convertValue(
                savedRight.getRightsContent(),
                RightsResponse.class
            );
       
        return result;
    }



    public RightsResponse deleteAllRight(Long id) {
        Rights right = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Right not found")
        );

        repository.delete(right);

        RightsResponse content = objectMapper.convertValue(
            right.getRightsContent(),
            RightsResponse.class
        );
       
        return content;
    }
}