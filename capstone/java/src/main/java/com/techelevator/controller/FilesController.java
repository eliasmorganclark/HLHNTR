package com.techelevator.controller;

import com.techelevator.model.fileStoage.ResponseMessage;
import com.techelevator.service.FilesStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin
public class FilesController {
    @Autowired
    FilesStorageService storageService;
    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String hazardId) {
        String message = "";
        try {
            String fileExtension = "." + file.getContentType().split("/")[1];
            System.out.println(fileExtension);
            storageService.save(file, hazardId + fileExtension);
            message = "Uploaded the file successfully: " + file.getOriginalFilename();
            //trigger spring to reload resource folder
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
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
