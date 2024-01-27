package com.saxnart.Saxnart.utility;

import com.saxnart.Saxnart.extention.ImageException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Component
public class FileUtils {
    // Validate file
    public void validateFile(MultipartFile file) {
        // Kiểm tra tên file
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.isEmpty()) {
            throw new ImageException("tên file không được để trống");
        }

        // image.png -> png
        // avatar.jpg -> jpg
        // Kiểm tra đuôi file (jpg, png, jpeg)
        String fileExtension = getFileExtension(fileName);
        if (!checkFileExtension(fileExtension)) {
            throw new ImageException("file không đúng định dạng");
        }

        // Kiểm tra dung lượng file (<= 2MB)
        double fileSize = (double) (file.getSize() / 1_048_576);
        if (fileSize > 2) {
            throw new ImageException("file không được vượt quá 2MB");
        }
    }

    // Lấy extension của file (ví dụ png, jpg, ...)
    public String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        return fileName.substring(lastIndexOf + 1);
    }

    // Kiểm tra extension của file có được phép hay không
    public boolean checkFileExtension(String fileExtension) {
        List<String> extensions = new ArrayList<>(List.of("png", "jpg", "jpeg", "pdf"));
        return extensions.contains(fileExtension.toLowerCase());
    }
}
