package com.springboot.milezero.utility;

import com.springboot.milezero.exception.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Objects;

public class UploadUtility {

    public static void validateImage(MultipartFile imageFile) {
        if (imageFile != null && !imageFile.isEmpty()) {

            List<String> allowedExtensions = List.of("jpg", "jpeg", "pdf");

            String fileName = Objects.requireNonNull(imageFile.getOriginalFilename());

            String fileExtension = fileName.split("\\.")[1];

            if (!allowedExtensions.contains(fileExtension)) {
                throw new FileUploadException("Invalid file type. Only JPG, JPEG, and PDF files are allowed");
            }
        }
    }

    public static String saveImage(MultipartFile imageFile, String uploadDir) {

        // 1. Convert upload dir to path using nio paths
        Path uploadPath = Paths.get(uploadDir);

        String fileName = Objects.requireNonNull(imageFile.getOriginalFilename());

        // 2. Resolve filename to path
        Path filePath = uploadPath.resolve(fileName);

        // 3. Copy the contents into filepath
        try {
            Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FileUploadException("Failed to save image");
        }

        return fileName;
    }
}
