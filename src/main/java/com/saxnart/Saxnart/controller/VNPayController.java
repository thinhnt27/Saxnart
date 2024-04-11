package com.saxnart.Saxnart.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.saxnart.Saxnart.config.VNPayConfig;
import com.saxnart.Saxnart.entity.BookingEntity;
import com.saxnart.Saxnart.model.ResponeCustom;
import com.saxnart.Saxnart.model.ResponseObject;
import com.saxnart.Saxnart.repository.BookingRepository;
import com.saxnart.Saxnart.service.BookingService;
import com.saxnart.Saxnart.service.VNPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class VNPayController {

    @Autowired
    private VNPayService vnPayService;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;
//    @GetMapping("/pay")
//    public ResponseEntity<ResponseObject> getPay(@RequestParam int totalPrice){
//        try {
//            String url = vnPayService.getPayUrl(totalPrice);
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject("failed", "Success", url));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                    .body(new ResponseObject("failed", e.getMessage(), ""));
//        }
//
//    }


    @GetMapping("/vnpay-payment")
    public ResponseEntity<ResponeCustom> getPaymentInfor(@RequestParam(value = "vnp_Amount") String vnp_Amount,
                                                          @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
//                                                          @RequestParam(value = "vnp_BankTranNo") String vnp_BankTranNo,
                                                          @RequestParam(value = "vnp_CardType") String vnp_CardType,
                                                          @RequestParam(value = "vnp_OrderInfo") String vnp_OrderInfo,
                                                          @RequestParam(value = "vnp_PayDate") String vnp_PayDate,
                                                          @RequestParam(value = "vnp_ResponseCode") String vnp_ResponseCode,
                                                          @RequestParam(value = "vnp_TmnCode") String vnp_TmnCode,
                                                          @RequestParam(value = "vnp_TransactionNo") String vnp_TransactionNo,
                                                          @RequestParam(value = "vnp_TransactionStatus") String vnp_TransactionStatus,
                                                          @RequestParam(value = "vnp_TxnRef") String vnp_TxnRef,
                                                          @RequestParam(value = "vnp_SecureHash") String vnp_SecureHash) {

        Map fields = new HashMap();
        try {
            encodeAndPut(fields, "vnp_Amount", vnp_Amount);
            encodeAndPut(fields, "vnp_BankCode", vnp_BankCode);
//            encodeAndPut(fields, "vnp_BankTranNo", vnp_BankTranNo);
            encodeAndPut(fields, "vnp_CardType", vnp_CardType);
            encodeAndPut(fields, "vnp_OrderInfo", vnp_OrderInfo);
            encodeAndPut(fields, "vnp_PayDate", vnp_PayDate);
            encodeAndPut(fields, "vnp_ResponseCode", vnp_ResponseCode);
            encodeAndPut(fields, "vnp_TmnCode", vnp_TmnCode);
            encodeAndPut(fields, "vnp_TransactionNo", vnp_TransactionNo);
            encodeAndPut(fields, "vnp_TransactionStatus", vnp_TransactionStatus);
            encodeAndPut(fields, "vnp_TxnRef", vnp_TxnRef);
            encodeAndPut(fields, "vnp_SecureHash", vnp_SecureHash);

            if (fields.containsKey("vnp_SecureHashType"))
            {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash"))
            {
                fields.remove("vnp_SecureHash");
            }
//            fields.remove("vnp_SecureHashType");
//            fields.remove("vnp_SecureHash");
            try {
                String signValue = VNPayConfig.hashAllFields(fields);
                if (signValue.equals(vnp_SecureHash)) {
                    boolean checkOrderId = true; // vnp_TxnRef exists in your database
                    boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                    boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)
                    Optional<BookingEntity> bookingEntity = bookingRepository.findById(Long.valueOf(vnp_TxnRef));
                    if (bookingEntity.isPresent()) {
                        // Đơn hàng tồn tại trong cơ sở dữ liệu
                        checkOrderId = true;
                        // Kiểm tra trạng thái đơn hàng
                        if (bookingEntity.get().getIsPayment() == false) {
                            // Đơn hàng đang ở trạng thái "pending"
                            checkOrderStatus = true;
                        } else {
                            // Đơn hàng đã được xác nhận hoặc hủy bỏ
                            checkOrderStatus = false;
                        }
                    } else {
                        // Đơn hàng không tồn tại trong cơ sở dữ liệu
                        checkOrderId = false;
                    }

                    if (checkOrderId) {
                        if (checkAmount) {
                            if (checkOrderStatus) {
                                if ("00".equals(vnp_ResponseCode)) {
                                    // Update PaymnentStatus = 1 into your Database
                                    bookingService.paymentVNPaySuccess(Long.valueOf(vnp_TxnRef));
                                    return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("00", "Confirm Success"));
                                } else {
                                    return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("00", "Confirm Success"));
//                                    // Update PaymnentStatus = 2 into your Database
//                                    return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("02", "Order already confirmed"));
                                }
                            } else {
                                return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("02", "Order already confirmed"));
                            }
                        } else {
                            return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("04", "Invalid Amount"));
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("01", "Order not Found"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("97", "Invalid Checksum"));
                }
            } catch(Exception e) {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponeCustom("99", "Unknown error"));
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponeCustom("failed", e.getMessage()));
        }

    }

    private void encodeAndPut(Map < String, String > fields, String paramName, String paramValue)
            throws UnsupportedEncodingException {
            String encodedName = URLEncoder.encode(paramName, StandardCharsets.US_ASCII.toString());
            String encodedValue = URLEncoder.encode(paramValue, StandardCharsets.US_ASCII.toString());
            fields.put(encodedName, encodedValue);
    }

}
