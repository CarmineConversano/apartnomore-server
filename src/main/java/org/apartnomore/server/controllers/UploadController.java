package org.apartnomore.server.controllers;

import org.apartnomore.server.exceptions.FileStorageException;
import org.apartnomore.server.models.Image;
import org.apartnomore.server.payload.response.UploadResponse;
import org.apartnomore.server.services.StorageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final String logPattern = "[UPLOAD CONTROLLER]";


    private final StorageServiceImpl storageService;

    public UploadController(StorageServiceImpl storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<?> uploadImage(
            @RequestParam MultipartFile file,
            HttpServletRequest request
    ) {
        try {
            if (file.isEmpty()) {
                UploadResponse uploadResponse = new UploadResponse("Empty file", null, null);
                return ResponseEntity.status(400).body(uploadResponse);
            }

            Image image = storageService.saveImage(file,
                    request.getSession().getServletContext().getRealPath("/"));
            return new ResponseEntity<>(new UploadResponse("Image uploaded!", image.getPath(), image.getId()), HttpStatus.CREATED);
        } catch (IOException e) {
            logger.error("{} - Couldn't upload image. IOException was thrown: {}", logPattern, e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<?> handleStorageFileNotFound(FileStorageException exc) {
        return ResponseEntity.notFound().build();
    }
}
