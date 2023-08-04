package org.apartnomore.server.services;

import org.apache.commons.io.FilenameUtils;
import org.apartnomore.server.controllers.UploadController;
import org.apartnomore.server.exceptions.ImageNotFoundException;
import org.apartnomore.server.models.Image;
import org.apartnomore.server.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class StorageServiceImpl implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);
    private static final String logPattern = "[STORAGE SERVICE]";

    private final ImageRepository imageRepository;
    @Value("${application.images.path}")
    private String baseImagesPath;

    StorageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image saveImage(MultipartFile file, String realPath) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if (Objects.requireNonNull(extension).isEmpty()) {
            extension = "jpg";
        }

        String uuid = UUID.randomUUID().toString().replace("-", "");

        String filePath = uuid + "." + extension;

        logger.info("{} Trying to write image to disk", logPattern);
        Files.copy(file.getInputStream(), Paths.get(baseImagesPath, filePath));

        Image image = new Image();
        image.setPath(filePath);

        return imageRepository.save(image);
    }

    @Override
    public Image findById(Long id) throws ImageNotFoundException {
        Optional<Image> image = this.imageRepository.findById(id);
        if (image.isEmpty()) {
            throw new ImageNotFoundException("Image not found with provided id");
        } else {
            return image.get();
        }
    }
}
