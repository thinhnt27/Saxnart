package com.saxnart.Saxnart.dto.response;

import com.saxnart.Saxnart.dto.TicketDTO;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO {
    private Long id;
    private String title;
    private Date showDate;
    private String picture;
    private String author;
    private Boolean isSpecial;
    private String content;
    private String image;
    private List<TicketDTO> ticketType;
}
