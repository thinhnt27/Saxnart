package com.saxnart.Saxnart.service;

import com.saxnart.Saxnart.dto.TicketDTO;
import com.saxnart.Saxnart.dto.response.ShowDTO;
import com.saxnart.Saxnart.entity.GalleryEntity;
import com.saxnart.Saxnart.entity.ShowEntity;
import com.saxnart.Saxnart.entity.TicketTypeEntity;
import com.saxnart.Saxnart.extention.ShowException;
import com.saxnart.Saxnart.repository.ShowRepository;
import com.saxnart.Saxnart.repository.TicketTypeRepository;
import com.saxnart.Saxnart.utility.ConvertDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Service
public class ShowService {

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private  TicketTypeRepository ticketTypeRepository;


    public ShowEntity findById(Long id) {
        return showRepository.findById(id).orElse(null);
    }

    public ShowDTO getShowById(Long id){
        ShowEntity show = showRepository.findById(id).orElse(null);
        if(show != null){
            return ConvertDTO.convertToShowDTO(show);
        }else throw new ShowException("Show with this id dose not exit");
    }

    public List<ShowDTO> getAllShow() {
        List<ShowEntity> shows = showRepository.findAll();
        List<ShowDTO> showDTOs = ConvertDTO.convertToShowDTOList(shows);
        return showDTOs;
    }

//    public List<ShowDTO> getAllShowDTO(){
//        List<ShowEntity> shows = showRepository.findAll();
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }
    public List<ShowEntity> getShowHaveSpecialIsTrue(){
        return showRepository.findByIsSpecialIsTrue();
    }
//    public List<ShowDTO> getShowDTOHaveSpecialIsTrue(){
//        List<ShowEntity> shows = showRepository.findByIsSpecialIsTrue();
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }


    public List<ShowEntity> getShowHaveSpecialIsFalse(){
        return showRepository.findByIsSpecialIsFalse();
    }

//    public List<ShowDTO> getShowDTOHaveSpecialIsFalse(){
//        List<ShowEntity> shows = showRepository.findByIsSpecialIsFalse();
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }


    public String createShow(ShowDTO show) throws Exception {
        ShowEntity showEntity = ConvertDTO.convertToShowEntity(show);
        if(!isShowDateExist(show)){
            for (TicketTypeEntity ticketType : showEntity.getTicketShows()) {
                ticketType.setShowtime(showEntity);
            }
            showEntity.setTicketShows(showEntity.getTicketShows());
            showRepository.save(showEntity);
            return "Success";
        }
        throw new Exception("Show cùng ngày đã tồn tại.");
    }

    public boolean isShowDateExist(ShowDTO show){
        List<ShowEntity> shows = showRepository.findAll();
        LocalDate showDate = show.getShowDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return shows.stream()
                .map(ShowEntity::getShowDate)
                .filter(Objects::nonNull)
                .map(date -> date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .anyMatch(entityShowDate -> entityShowDate.equals(showDate));
    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsTrue(Date currentDate) {
        return showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
    }

//    public List<ShowDTO> findShowsDTOAfterDateAndSpecialIsTrue(Date currentDate) {
//        List<ShowEntity> shows = showRepository.findSpecialTrueShowsAfterCurrentDate(currentDate);
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }

    public List<ShowEntity> findShowsAfterDateAndSpecialIsFalse(Date currentDate) {
        return showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
    }

//    public List<ShowDTO> findShowsDTOAfterDateAndSpecialIsFalse(Date currentDate) {
//        List<ShowEntity> shows = showRepository.findSpecialFalseShowsAfterCurrentDate(currentDate);
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }

    public List<ShowEntity> findShowsAfterDate(Date currentDate) {
        return showRepository.findShowsAfterCurrentDate(currentDate);
    }

//    public List<ShowDTO> findShowsDTOAfterDate(Date currentDate) {
//        List<ShowEntity> shows = showRepository.findShowsAfterCurrentDate(currentDate);
//        List<TicketTypeEntity> tickets = ticketTypeRepository.findAll();
//        List<ShowDTO> showDTOs = ConvertDTO.convertShowsToDTOs(shows, tickets);
//        return showDTOs;
//    }

    public ShowEntity updateShow(Long showId, ShowDTO updatedShowDTO) throws Exception {

        if (isShowDateExist(updatedShowDTO)){
            throw new Exception("Show cùng ngày đã tồn tại.");
        }
        // Kiểm tra xem show có tồn tại trong cơ sở dữ liệu không
        ShowEntity existingShow = showRepository.findById(showId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy show với id: " + showId));

        // Cập nhật thông tin của show từ updatedShowDTO
        existingShow.setTitle(updatedShowDTO.getTitle());
        existingShow.setShowDate(updatedShowDTO.getShowDate());
        existingShow.setPicture(updatedShowDTO.getPicture());
        existingShow.setAuthor(updatedShowDTO.getAuthor());
        existingShow.setIsSpecial(updatedShowDTO.getIsSpecial());
        existingShow.setContent(updatedShowDTO.getContent());

        // Cập nhật thông tin các loại vé
        updateTicketTypes(existingShow, updatedShowDTO.getTicketType());

        // Lưu show đã cập nhật vào cơ sở dữ liệu và trả về
        return showRepository.save(existingShow);
    }
    private void updateTicketTypes(ShowEntity showEntity, List<TicketDTO> updatedTicketDTOs) {
        if (updatedTicketDTOs != null) {
            // Lặp qua danh sách các vé được cập nhật
            for (TicketDTO updatedTicketDTO : updatedTicketDTOs) {
                // Tìm vé trong showEntity dựa trên tên
                Optional<TicketTypeEntity> ticketTypeEntityOptional = showEntity.getTicketShows().stream()
                        .filter(ticketTypeEntity -> ticketTypeEntity.getName().equals(updatedTicketDTO.getName()))
                        .findFirst();

                // Nếu vé tồn tại, cập nhật thông tin
                ticketTypeEntityOptional.ifPresent(ticketTypeEntity -> {
                    ticketTypeEntity.setPrice(updatedTicketDTO.getPrice());
                    // Cập nhật các thuộc tính khác nếu cần
                });

                // Nếu vé không tồn tại, tạo mới vé và thêm vào showEntity
                if (ticketTypeEntityOptional.isEmpty()) {
                    TicketTypeEntity newTicketTypeEntity = ConvertDTO.convertToTicketTypeEntity(updatedTicketDTO);
                    newTicketTypeEntity.setShowtime(showEntity);
                    showEntity.getTicketShows().add(newTicketTypeEntity);
                }
            }
        }
    }

    public String deleteShowById(Long showId) {
        // Kiểm tra xem show có tồn tại không
        ShowEntity showEntity = showRepository.findById(showId)
                .orElse(null);
        if (showEntity == null) {
            return "Show với id này không tồn tại";
        }

        // Xóa tất cả các vé liên quan đến show
        ticketTypeRepository.deleteByShowtime_Id(showId);

        // Xóa show
        showRepository.delete(showEntity);
        return "delete success";
    }

    public String updateStatus(Long id) {
        ShowEntity showEntity = showRepository.findById(id).orElse(null);
        if (showEntity != null) {
            showEntity.setStatus(!showEntity.getStatus());
            showRepository.save(showEntity);
            return "Update thành công";
        }
        return "Không tìm thấy";
    }


}
