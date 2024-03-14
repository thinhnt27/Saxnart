package com.saxnart.Saxnart.service;


import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.extention.ChuyenNgheSixException;
import com.saxnart.Saxnart.repository.ChuyenNgheSiRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

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
        chuyenNgheSiEntity.setCreateDate(new Date());
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
            chuyenNgheSi.setStatus(!chuyenNgheSi.getStatus());
            chuyenNgheSiRepository.save(chuyenNgheSi);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }

    public ChuyenNgheSiEntity update(Long id, ChuyenNgheSiEntity updatedChuyenNgheSi) {
        Optional<ChuyenNgheSiEntity> existingChuyenNgheSiOptional = chuyenNgheSiRepository.findById(id);
        updatedChuyenNgheSi.setDateModified(new Date());
        if (existingChuyenNgheSiOptional.isPresent()) {
            ChuyenNgheSiEntity existingChuyenNgheSi = existingChuyenNgheSiOptional.get();
            ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setSkipNullEnabled(true);
            modelMapper.map(updatedChuyenNgheSi, existingChuyenNgheSi);

            chuyenNgheSiRepository.save(existingChuyenNgheSi);
            return existingChuyenNgheSi;
        } else {
            throw new ChuyenNgheSixException("ChuyenNgheSi not found");
        }
    }

}
