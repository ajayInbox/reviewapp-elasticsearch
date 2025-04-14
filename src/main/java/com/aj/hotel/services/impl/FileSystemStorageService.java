package com.aj.hotel.services.impl;

import com.aj.hotel.exceptions.StorageException;
import com.aj.hotel.services.StorageService;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Service
@Slf4j
public class FileSystemStorageService implements StorageService {

    @Value("${app.storage.location:uploads}")
    private String storageLocation;

    private Path rootLocation;

    @PostConstruct
    public void init(){
        rootLocation = Paths.get(storageLocation);
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Couldn't store, Invalid storage path",e);
        }
    }


    @Override
    public String store(MultipartFile file, String fileName) {
        if(file.isEmpty()){
            throw new StorageException("file is empty");
        }

        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        String finalFileName = fileName + "." + extension;

        Path destinationFile = rootLocation.resolve(Paths.get(finalFileName))
                .normalize()
                .toAbsolutePath();

        if(!destinationFile.getParent().equals(rootLocation.toAbsolutePath())){
            throw new StorageException("couldn't save outside specified directory");
        }

        try(InputStream inputStream = file.getInputStream()){
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new StorageException("couldn't save file",e);
        }
        return finalFileName;
    }

    @Override
    public Optional<Resource> loadAsResource(String fileName) {
        try {
            Path file = rootLocation.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if(resource.exists() || resource.isReadable()){
                return Optional.of(resource);
            }
            return Optional.empty();
        }catch (MalformedURLException e){
            log.warn("couldn't save file with fileName: {}",fileName,e);
            return Optional.empty();
        }
    }
}
