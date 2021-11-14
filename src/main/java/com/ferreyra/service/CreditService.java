package com.ferreyra.service;

import com.ferreyra.exceptions.InvalidRequestException;
import com.ferreyra.exceptions.RecordNotExistsException;
import com.ferreyra.model.Credit;
import com.ferreyra.model.User;
import com.ferreyra.repository.CreditRepository;
import com.ferreyra.repository.UserRepository;
import com.ferreyra.utils.QRCodeGenerator;
import com.ferreyra.utils.TelegramUtils;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;


@Service
public class CreditService {

    private static final String QR_CODE_IMAGE_PATH = "./src/main/resources/QRCode.png";
    private final CreditRepository creditRepository;
    private final UserRepository userRepository;

    @Autowired
    public CreditService(CreditRepository creditRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.creditRepository = creditRepository;
    }

    public void addCredit(Integer userId, String chatId, Double price, Integer quantity) throws InvalidRequestException, RecordNotExistsException, IOException, WriterException {

        User user = userRepository.getById(userId);
        for(int i = 0; i < quantity; i++) {
            String code = UUID.randomUUID().toString();
            Credit credit = new Credit(user, price, true, code);
            Integer id = creditRepository.addCredit(user.getId(), price, true, code);
            if (id == null) {
                throw new InvalidRequestException("Credit can not be created");
            }
            QRCodeGenerator.generateQRCodeImage(code, 1000, 1000, QR_CODE_IMAGE_PATH);
            TelegramUtils.sendQr(chatId, QR_CODE_IMAGE_PATH);
        }
    }

    public Boolean readCredit(Integer userId, String hashCode) {
        Boolean flag = false;
        List<Credit> credits= creditRepository.getCreditsByUser(userId);
        for(int i = 0; i < credits.size(); i++) {
            if (hashCode.equals(credits.get(i).getHashCode())){
                if (credits.get(i).getActive() == true){
                    creditRepository.updateCredit(credits.get(i).getId());
                    flag = true;
                }
            }
        }
        return flag;
    }

}
