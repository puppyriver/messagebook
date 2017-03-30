package com.infox.messagebook.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Ronnie.Chen
 * Date: 2016/10/10
 * Time: 15:42
 * rongrong.chen@alcatel-sbell.com.cn
 */
@Controller
@RequestMapping("/file/*")
public class FileController extends AbstractAjaxController {
    private Logger logger = LoggerFactory.getLogger(FileController.class);

    @RequestMapping(value="poll")
    public @ResponseBody
    HashMap poll(HttpServletRequest request, HttpServletResponse response) throws IOException {

        return commonPoll(request,response);
    }
    @RequestMapping(value="upload")
    public @ResponseBody
    HashMap upload(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HashMap map = new HashMap();
        String key = request.getParameter("key");



        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        //   factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("utf-8");
        // 设置最大文件上传值
        //  upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        //    upload.setSizeMax(MAX_REQUEST_SIZE);

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
        String uploadPath = request.getServletContext().getRealPath("/") + File.separator +"uploads";


        // 如果目录不存在则创建
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
            List<FileItem> formItems = upload.parseRequest(request);
            List<File> storeFiles = new ArrayList<>();
            if (formItems != null && formItems.size() > 0) {
                String group = SysUtil.nextDN();
                EventManager.getInstance().addGroup(group);
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        String filePath = uploadPath + File.separator + fileName;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        map.put("result","success");

                        map.put("group", group);

                        storeFiles.add(storeFile);
//                        request.setAttribute("message",
//                                "文件上传成功!");


                    }
                }


                if (key != null && key.equals("virus")) {
                        new CusEventsImporter(storeFiles ,group).start();
                }  else {

                    Thread t = new Thread() {
                        public void run() {
                            try {
                                new EventsLogImporter(group).importData(storeFiles, R_Customer.class);
                            } catch (Exception e) {
                                logger.error(e.getMessage(), e);
                            }
                            EventManager.getInstance().groupEnd(group);
                        }
                    };
                    t.start();
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
            logger.error(ex.getMessage(),ex);
        }

//        for (int i = 0; i < 10; i++) {
//            response.getWriter().write("{time-"+i+":\"test\"}");
//            response.getWriter().flush();
//            try {
//                Thread.sleep(1000l);
//            } catch (InterruptedException e) {
//                logger.error(e.getMessage(), e);
//            }
//        }


        return map;
    }


}
