package com.myspring.demo.rights;

import java.util.List;
import java.util.function.Function;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myspring.demo.rights.dto.Right;
import com.myspring.demo.rights.dto.RightsResponse;



@Service
public class RightsService<R> {

    private final RightsRepository repository;
     private final ObjectMapper objectMapper;

    public RightsService(RightsRepository repository,  ObjectMapper objectMapper){
        this.repository = repository;
        this.objectMapper = objectMapper;
    }


    public RightsResponse getOneRight(Long id) {
        Rights oneRight = repository.findById(id).orElseThrow(
            () -> new RuntimeException("Right not found")
        );

         RightsResponse content = objectMapper.convertValue(
            oneRight.getRightsContent(),
            RightsResponse.class
        );
       
            return content;
        
    }

     public RightsResponse saveOneRight(Right values) {

        Rights oneRight = new Rights();
       
        oneRight.setRightsContent(values.content());
        oneRight.setRightsId(values.User_Id());

        Rights savedRight = repository.save(oneRight);
        
        RightsResponse content = objectMapper.convertValue(
            savedRight.getRightsContent(),
            RightsResponse.class
        );
       
            return content;
    }

    public List<RightsResponse> getAllRights() {
        List<RightsResponse> allRights = (List<RightsResponse>) repository.findAll()
        .stream()
        .map((Function<? super Rights, ? extends R>) (right) ->  {
            objectMapper.convertValue(
            right.getRightsContent(), RightsResponse.class);
            return null;
        }).toList();

        return allRights;
    }


    public RightsResponse deleteOneRight(Long id) {
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