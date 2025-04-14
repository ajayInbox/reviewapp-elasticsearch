package com.aj.hotel.services;

import com.aj.hotel.domain.entities.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface ImageService {

    Image uploadImage(MultipartFile file);
    Optional<Resource> retrieveImageAsResource(String fileName);

}
