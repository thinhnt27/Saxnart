package com.saxnart.Saxnart.dto.response;

import com.saxnart.Saxnart.entity.BookingDetailEntity;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingDetailResponse {
    private Long bookingId;
    private List<BookingDetailEntity> bookingDetailEntities;
}
