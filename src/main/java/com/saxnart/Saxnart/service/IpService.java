package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.IpEntity;
import com.saxnart.Saxnart.repository.IpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class IpService {
    @Autowired
    private IpRepository ipRepository;

    public List<IpEntity> getAllIps() {
        return ipRepository.findAll();
    }

    public IpEntity getIpById(Long id) {
        Optional<IpEntity> ipOptional = ipRepository.findById(id);
        return ipOptional.orElse(null);
    }

    public IpEntity createIp(IpEntity ipEntity) {
        ipEntity.setTime(new Date());
        return ipRepository.save(ipEntity);
    }

    public void deleteIp(Long id) {
        ipRepository.deleteById(id);
    }
}
