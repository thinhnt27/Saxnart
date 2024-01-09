package com.saxnart.Saxnart.service;


import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.repository.ChuyenNgheSiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChuyenNgheSiService {
    @Autowired
    private ChuyenNgheSiRepository chuyenNgheSiRepository;

    public List<ChuyenNgheSiEntity> getAllChuyenNgheSi() {
        return chuyenNgheSiRepository.findAll();
    }

    public ChuyenNgheSiEntity getChuyenNgheSiById(Long id) {
        return chuyenNgheSiRepository.findById(id).orElse(null);
    }

    public ChuyenNgheSiEntity saveChuyenNgheSi(ChuyenNgheSiEntity chuyenNgheSiEntity) {
        return chuyenNgheSiRepository.save(chuyenNgheSiEntity);
    }

    public String deleteChuyenNgheSi(Long id) {
        if (chuyenNgheSiRepository.existsById(id)) {
            chuyenNgheSiRepository.deleteById(id);
            return "Xóa thành công";
        } else {
            return "Không tìm thấy bản ghi để xóa";
        }
    }

    public String updateStatus(Long id) {
        ChuyenNgheSiEntity chuyenNgheSi = chuyenNgheSiRepository.findById(id).orElse(null);
        if (chuyenNgheSi != null) {
            chuyenNgheSi.setStatus(false);
            chuyenNgheSiRepository.save(chuyenNgheSi);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }

}
