package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.entity.BlogEntity;
import com.saxnart.Saxnart.entity.ChuyenNgheSiEntity;
import com.saxnart.Saxnart.extention.ChuyenNgheSixException;
import com.saxnart.Saxnart.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BlogService {

    @Autowired
    private BlogRepository blogRepository;

    public List<BlogEntity> getAllBlog() {
        return blogRepository.findAll();
    }

    public BlogEntity getBlogById(Long id) {
        return blogRepository.findById(id).orElse(null);
    }

    public BlogEntity saveBlog(BlogEntity blogEntity) {
        return blogRepository.save(blogEntity);
    }

    public String deleteBlog(Long id) {
        if (blogRepository.existsById(id)) {
            blogRepository.deleteById(id);
            return "Xóa thành công";
        } else {
            return "Không tìm thấy bản ghi để xóa";
        }
    }

    public String updateStatus(Long id) {
        BlogEntity blogEntity = blogRepository.findById(id).orElse(null);
        if (blogEntity != null) {
            blogEntity.setStatus(!blogEntity.getStatus());
            blogRepository.save(blogEntity);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }

    public BlogEntity update(Long Id, BlogEntity blogEntity){
        Optional<BlogEntity> blog = blogRepository.findById(Id);
        if (blog.isPresent()){
            BlogEntity entity = this.getBlogById(Id);
            entity.setTitle(blogEntity.getTitle());
            entity.setAuthor(blogEntity.getAuthor());
            entity.setContent(blogEntity.getContent());
            entity.setCreateDate(blogEntity.getCreateDate());
            entity.setStatus(blogEntity.getStatus());
            blogRepository.save(entity);
            return entity;
        }
        throw new ChuyenNgheSixException("updated failed");
    }
}
