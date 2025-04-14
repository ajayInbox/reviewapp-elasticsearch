package com.aj.hotel.services.impl;

import com.aj.hotel.domain.entities.Image;
import com.aj.hotel.services.ImageService;
import com.aj.hotel.services.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final StorageService storageService;

    @Override
    public Image uploadImage(MultipartFile file) {
        String imageId = UUID.randomUUID().toString();
        String url = storageService.store(file, imageId);

        return Image.builder()
                .url(url)
                .uploadTime(LocalDateTime.now())
                .build();
    }

    @Override
    public Optional<Resource> retrieveImageAsResource(String fileName) {
        return storageService.loadAsResource(fileName);
    }
}
