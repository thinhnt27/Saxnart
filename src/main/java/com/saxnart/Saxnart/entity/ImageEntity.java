package com.saxnart.Saxnart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "Gallery")
public class ImageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Lob // Large Object
    @Column(name = "image", columnDefinition = "LONGBLOB")
    private byte[] image;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @Column
    private Boolean status;

    @PrePersist
    public void prePersist() {
        createdDate = LocalDateTime.now();
    }

    public ImageEntity(String name, String type, byte[] image, Boolean status) {
        this.name = name;
        this.type = type;
        this.image = image;
        this.status = status;
    }
}
