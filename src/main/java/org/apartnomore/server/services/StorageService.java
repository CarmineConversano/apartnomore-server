package org.apartnomore.server.services;

import org.apartnomore.server.exceptions.ImageNotFoundException;
import org.apartnomore.server.models.Image;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    Image saveImage(MultipartFile file, String realPath) throws IOException;

    Image findById(Long id) throws ImageNotFoundException;
}
