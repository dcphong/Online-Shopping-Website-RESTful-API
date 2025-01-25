package com.final_test_sof3012.sof3022_ass_restful_api.models;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseObject<T> {

    String status;
    String message;
    T data;
    LocalDateTime timestamp;

    public ResponseObject(String status,String message,T data){
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }



}
