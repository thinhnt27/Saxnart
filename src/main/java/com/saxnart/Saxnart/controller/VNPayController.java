package com.saxnart.Saxnart.controller;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.saxnart.Saxnart.config.VNPayConfig;
import com.saxnart.Saxnart.model.ResponseObject;
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
    public ResponseEntity<ResponseObject> getPaymentInfor(@RequestParam(value = "vnp_Amount") String vnp_Amount,
                                                          @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
                                                          @RequestParam(value = "vnp_BankTranNo") String vnp_BankTranNo,
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
            encodeAndPut(fields, "vnp_BankTranNo", vnp_BankTranNo);
            encodeAndPut(fields, "vnp_CardType", vnp_CardType);
            encodeAndPut(fields, "vnp_OrderInfo", vnp_OrderInfo);
            encodeAndPut(fields, "vnp_PayDate", vnp_PayDate);
            encodeAndPut(fields, "vnp_ResponseCode", vnp_ResponseCode);
            encodeAndPut(fields, "vnp_TmnCode", vnp_TmnCode);
            encodeAndPut(fields, "vnp_TransactionNo", vnp_TransactionNo);
            encodeAndPut(fields, "vnp_TransactionStatus", vnp_TransactionStatus);
            encodeAndPut(fields, "vnp_TxnRef", vnp_TxnRef);
            encodeAndPut(fields, "vnp_SecureHash", vnp_SecureHash);

            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");
            String signValue = VNPayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(vnp_TransactionStatus)) {
                    bookingService.paymentVNPaySuccess(Long.valueOf(vnp_TxnRef));
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", "GD Thanh cong", ""));
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "GD Khong thanh cong", ""));
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("error", "Chu ky khong hop le", ""));
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ResponseObject("failed", e.getMessage(), ""));
        }

    }

    private void encodeAndPut(Map < String, String > fields, String paramName, String paramValue)
            throws UnsupportedEncodingException {
            String encodedName = URLEncoder.encode(paramName, StandardCharsets.US_ASCII.toString());
            String encodedValue = URLEncoder.encode(paramValue, StandardCharsets.US_ASCII.toString());
            fields.put(encodedName, encodedValue);
    }

}
