package com.final_test_sof3012.sof3022_ass_restful_api.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
public class CloudinaryService {

    private static final Logger logger = LoggerFactory.getLogger(CloudinaryService.class);

    Cloudinary cloudinary;

    @Transactional
    public String uploadImage(MultipartFile file) throws IOException {
        try {
            String originalFileName = file.getOriginalFilename();
            Map params1 = ObjectUtils.asMap(
                    "public_id",originalFileName.substring(0,originalFileName.lastIndexOf(".")),
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), params1);
            return uploadResult.get("url").toString();
        } catch (IOException e) {
            logger.error("Error uploading image to Cloudinary", e);
            throw e;
        }
    }

}
