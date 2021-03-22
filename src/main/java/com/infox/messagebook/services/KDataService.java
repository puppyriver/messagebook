package com.infox.messagebook.services;

import com.infox.messagebook.repository.KDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@Service
@EnableScheduling
public class KDataService {
    @Autowired
    private KDataRepository kDataRepository;


    @Scheduled(cron = "0 0/5 * * * ?")
    public void run() {
        kDataRepository.findAll().stream().filter(k-> Objects.equals(k.getKtype(),"EXPIRE_MINUTES")).forEach(kData -> {
            if (kData.getTime() != null && (System.currentTimeMillis() - kData.getTime().getTime()) > 60 * 1000 * Long.parseLong(kData.getKvalue())) {
                sendForSms("STPD");
            }
        });
    }

    private Map sendForSms(String code) {
        Map map = new RestTemplate().getForObject("http://www.lovewinline.com:8086/admin/api/tmUser/sendSms?phone=18621984604&code="+code, Map.class);
        return  map;
    }
}
