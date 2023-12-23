package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.response.SeatResponse;
import com.saxnart.Saxnart.entity.SeatEntity;
import com.saxnart.Saxnart.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public SeatEntity findById(Long id) {
        return seatRepository.findById(id).orElse(null);
    }
    public List<SeatEntity> findBookedSeatsByShowtimeId(Long showtimeId) {
        return seatRepository.findBookedSeatsByShowtimeId(showtimeId);
    }

    public List<SeatResponse> findSeatsStatusByShow(Long showTimeId) {
        List<SeatEntity> seats = seatRepository.findAll();
        List<SeatEntity> seatShows = seatRepository.findBookedSeatsByShowtimeId(showTimeId);
        List<SeatResponse> seatResponses = new ArrayList<>();
        for (SeatEntity seatAll : seats) {
            boolean isBooked = seatShows.stream()
                    .anyMatch(seatShowAll -> seatShowAll.getId().equals(seatAll.getId()));
            SeatResponse seatResponse = new SeatResponse(seatAll.getId(),seatAll.getSeatNum(), isBooked);
            seatResponses.add(seatResponse);
        }

        return seatResponses;
    }
}
