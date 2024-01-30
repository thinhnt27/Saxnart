package com.saxnart.Saxnart.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class TicketDTO {

    private Long id;
    private String name;
    private double priceInternational;
    private double weekdayPrice;
    private double weekendPrice;
}
