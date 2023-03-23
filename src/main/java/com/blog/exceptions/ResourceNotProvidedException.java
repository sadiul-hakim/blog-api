package com.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotProvidedException extends Exception{
    public ResourceNotProvidedException(String resourceName){
        super(String.format("Resource '%s' is not provided.",resourceName));
    }
}
