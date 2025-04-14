package com.aj.hotel.controllers;

import com.aj.hotel.domain.dtos.ImageDto;
import com.aj.hotel.domain.entities.Image;
import com.aj.hotel.mappers.ImageMapper;
import com.aj.hotel.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;
    private final ImageMapper imageMapper;

    @PostMapping
    public ResponseEntity<ImageDto> uploadImage(@RequestParam("file")MultipartFile file){
        Image image = imageService.uploadImage(file);
        return new ResponseEntity<>(imageMapper.toDto(image), HttpStatus.OK);
    }

    @GetMapping(path = "/{id:.+}")
    public ResponseEntity<Resource> getImage(@PathVariable String id){
        return imageService.retrieveImageAsResource(id).map(
                image -> ResponseEntity.ok()
                        .contentType(MediaTypeFactory.getMediaType(image)
                        .orElse(MediaType.APPLICATION_OCTET_STREAM))
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline")
                        .body(image)
        ).orElse(ResponseEntity.notFound().build());
    }
}
