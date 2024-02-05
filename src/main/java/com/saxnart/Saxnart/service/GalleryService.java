package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.BlogEntity;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.entity.GalleryEntity;
import com.saxnart.Saxnart.repository.GalleryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    public List<GalleryEntity> getAllGallery() {
        return galleryRepository.findAll();
    }

    public GalleryEntity getGalleryById(Long id) {
        return galleryRepository.findById(id).orElse(null);
    }

    public GalleryEntity saveGallery(GalleryEntity galleryEntity) {
        return galleryRepository.save(galleryEntity);
    }

    public String deleteGallery(Long id) {
        if (galleryRepository.existsById(id)) {
            galleryRepository.deleteById(id);
            return "Xóa thành công";
        } else {
            return "Không tìm thấy bản ghi để xóa";
        }
    }

    public String updateStatus(Long id) {
        GalleryEntity galleryEntity = galleryRepository.findById(id).orElse(null);
        if (galleryEntity != null) {
            galleryEntity.setStatus(!galleryEntity.getStatus());
            galleryRepository.save(galleryEntity);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }

}
