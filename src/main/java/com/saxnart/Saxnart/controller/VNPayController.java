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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/payment")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "https://saxnartclub.com"})
public class VNPayController {

    @GetMapping("/pay")
    public String getPay(@RequestParam int totalPrice) throws UnsupportedEncodingException {

        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";
        long amount = totalPrice * 100L;
        //String bankCode = "";

        String vnp_TxnRef = VNPayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";

        String vnp_TmnCode = VNPayConfig.vnp_TmnCode;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        //vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);


        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+7"));
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayConfig.hmacSHA512(VNPayConfig.secretKey, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = VNPayConfig.vnp_PayUrl + "?" + queryUrl;

        return paymentUrl;
    }


    @GetMapping("/vnpay-payment")
    public ResponseEntity<ResponseObject> getPaymentInfor(@RequestParam(value = "vnp_Amount") String vnp_Amount,
                                                          @RequestParam(value = "vnp_BankCode") String vnp_BankCode,
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

            fields.remove("vnp_SecureHashType");
            fields.remove("vnp_SecureHash");
            String signValue = VNPayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {
                if ("00".equals(vnp_TransactionStatus)) {
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
