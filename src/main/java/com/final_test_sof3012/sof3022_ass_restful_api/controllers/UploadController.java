package com.final_test_sof3012.sof3022_ass_restful_api.controllers;

import com.final_test_sof3012.sof3022_ass_restful_api.models.ResponseObject;
import com.final_test_sof3012.sof3022_ass_restful_api.services.CloudinaryService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/cloudinary")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class UploadController {

    CloudinaryService cloudinaryService;

    @PostMapping("/upload/images")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file) {
        try{
            Map<String,Object> imageUrl = cloudinaryService.uploadImage(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("SUCCESS","Upload images successfully!",imageUrl)
            );
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(500).body(
                    new ResponseObject("ERROR","Image upload failed!",null)
            );
        }
    }

}
