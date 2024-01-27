package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.ImageEntity;
import com.saxnart.Saxnart.extention.ImageException;
import com.saxnart.Saxnart.repository.ImageRepository;
import com.saxnart.Saxnart.utility.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final FileUtils fileUtils;

    public List<ImageEntity> getAllImage() {
        return imageRepository.findByOrderByCreatedDateDesc();
    }

    public ImageEntity getImage(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> {
            throw new ImageException("Not found image with id = " + id);
        });
    }

    public ImageEntity uploadImage(MultipartFile file) {
        fileUtils.validateFile(file);

        try {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            ImageEntity imageEntity = new ImageEntity(fileName, file.getContentType(), file.getBytes(), true);
            return imageRepository.save(imageEntity);
        } catch (Exception e) {
            throw new RuntimeException("Upload image error");
        }
    }

    public void deleteImage(Long id) {
        ImageEntity imageEntity = imageRepository.findById(id).orElseThrow(() -> {
            throw new ImageException("Not found image with id = " + id);
        });

        imageRepository.delete(imageEntity);
    }

    public String updateStatus(Long id) {
        ImageEntity imageEntity = imageRepository.findById(id).orElse(null);
        if (imageEntity != null) {
            imageEntity.setStatus(!imageEntity.getStatus());
            imageRepository.save(imageEntity);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }
}
