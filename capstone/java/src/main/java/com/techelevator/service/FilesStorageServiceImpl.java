package com.techelevator.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FilesStorageServiceImpl implements FilesStorageService {
//    private final Path root = Paths.get("src/main/resources/static");
    private final Path root = Paths.get("C:\\Users\\Student\\workspace\\java-final-capstone-team-3\\capstone\\vue\\src\\img\\uploads");

    @Override
    public void save(MultipartFile file,String newFilename) {
        try {
            Files.copy(file.getInputStream(), this.root.resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }

}


//    Path temp = Files.move
//            (Paths.get("C:\\Users\\Mayank\\Desktop\\44.txt"),
//                    Paths.get("C:\\Users\\Mayank\\Desktop\\dest\\445.txt"));
//
//        if(temp != null)
//                {
//                System.out.println("File renamed and moved successfully");
//                }
//                else
//                {
//                System.out.println("Failed to move the file");
//                }