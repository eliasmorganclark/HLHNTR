package com.techelevator.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FilesStorageServiceImpl implements FilesStorageService {
    private final Path root = Paths.get("src/main/resources/static");

    @Override
    public void save(MultipartFile file,String newFilename) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

}
