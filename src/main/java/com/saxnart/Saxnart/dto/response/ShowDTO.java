package com.saxnart.Saxnart.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS", timezone = "Asia/Ho_Chi_Minh")
    private Date showDate;
    private String picture;
    private String author;
    private Boolean isSpecial;
    private String content;
    private Boolean status;
    private List<TicketDTO> ticketType;
}
