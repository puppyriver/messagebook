package com.infox.messagebook.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.infox.messagebook.api.JsonResponse;
import com.infox.messagebook.model.BMessage;
import com.infox.messagebook.model.KData;
import com.infox.messagebook.model.XMessage;
import com.infox.messagebook.repository.KDataRepository;
import com.infox.messagebook.repository.XMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;


/**
 * Author: Ronnie.Chen
 * Date: 2017/3/16
 * Time: 14:56
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Controller
@RequestMapping("/kdata/*")
public class KDataControler {
    
    @Autowired
    private KDataRepository kDataRepository;
    private Logger logger = LoggerFactory.getLogger(KDataControler.class);

    @RequestMapping(value = "save", method = {POST,GET})
    public @ResponseBody
    KData save(String kname, String kvalue ) {
        if (kname == null || kname.trim().isEmpty()) {
            throw new RuntimeException("kname can not be null or empty");
        }
        KData kData = kDataRepository.findByKname(kname);
        if (kData == null) {
            kData = new KData();
            kData.setKname(kname);
        }
        kData.setKvalue(kvalue);
        kData.setTime(new Date());
        return kDataRepository.save(kData);
    }

    @RequestMapping(value = "list", method = {POST,GET})
    public @ResponseBody
    String list( ) {
        List<KData> all = kDataRepository.findAll();
        ObjectMapper mapper = new ObjectMapper();
        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        String s = null;
        try {
            s = mapper.writeValueAsString(all);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return s;
    }
    @RequestMapping(value = "get", method = {POST,GET})
    public @ResponseBody
    KData get( String kname) {
       KData kData = kDataRepository.findByKname(kname);
       return kData;
    }

    @RequestMapping(value = "delete", method = {POST,GET})
    public @ResponseBody
    Map delete( String kname) {
        KData kData = kDataRepository.findByKname(kname);
        if (kData != null) {
            kDataRepository.delete(kData.getId());
            return ImmutableMap.of("success",1);
        }
        return ImmutableMap.of("success",0);
    }

    
}
