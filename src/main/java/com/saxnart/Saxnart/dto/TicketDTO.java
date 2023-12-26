package com.saxnart.Saxnart.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private Long id;
    private String name;
    private double priceInternational;
    private double weekdayPrice;
    private double weekendPrice;
}
