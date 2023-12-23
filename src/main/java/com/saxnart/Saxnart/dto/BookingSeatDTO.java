package com.saxnart.Saxnart.dto;

import lombok.*;

    @Data
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
public class BookingSeatDTO {
    private Long bookingId;
    private Long seatId;
}
