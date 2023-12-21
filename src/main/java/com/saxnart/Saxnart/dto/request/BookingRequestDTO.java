package com.saxnart.Saxnart.dto.request;

import com.saxnart.Saxnart.dto.TicketDTO;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequestDTO {

    private Long id;


    private String name;

    private String email;


    private String telephoneNum;


    private String content;


    private Date createdDate;


    private Boolean status;

    private List<TicketDTO> tickets;
}
