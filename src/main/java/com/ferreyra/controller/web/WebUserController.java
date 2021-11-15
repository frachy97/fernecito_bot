package com.ferreyra.controller.web;
import com.ferreyra.exceptions.InvalidRequestException;
import com.ferreyra.exceptions.RecordNotExistsException;
import com.ferreyra.exceptions.ValidationException;
import com.ferreyra.service.CreditService;
import com.google.zxing.WriterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class WebUserController {

    private final CreditService creditService;
    private static final Logger logger = LoggerFactory.getLogger(WebUserController.class);

    @Autowired
    public WebUserController(CreditService creditService) {
        this.creditService = creditService;
    }

    @GetMapping("generate-qr/{userId}/{quantity}/{chatId}")
    public String generateQr(@PathVariable Integer userId, @PathVariable String chatId, @PathVariable Integer quantity) throws InvalidRequestException, IOException, WriterException, RecordNotExistsException {
        logger.info("TEST EXITOSO: userId->"+ userId + " chatId->"+chatId);
        creditService.addCredit(userId, chatId, Double.valueOf("1"), quantity);
        return chatId;
    }

    @GetMapping("read-qr/{userId}/{hashCode}")
    public Boolean readQr(@PathVariable Integer userId, @PathVariable String hashCode) throws ValidationException {
        logger.info("TEST EXITOSO: userId->"+ userId + " hashCode->"+hashCode);
        return creditService.readCredit(userId, hashCode);
    }



    @GetMapping("test")
    public String test(){
        String msg= "Exitoso";
        logger.info("TEST EXITOSO");
        return msg;
    }

}
