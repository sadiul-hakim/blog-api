package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String resourceName,String resourceField,long fieldValue){
        super(String.format("%s not found with %s : %d",resourceName,resourceField,fieldValue));
    }
}
