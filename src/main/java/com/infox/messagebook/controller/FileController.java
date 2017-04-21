package com.infox.messagebook.controller;

import com.infox.messagebook.api.FileInfo;
import com.infox.messagebook.api.JsonResponse;
import com.infox.messagebook.utils.EventManager;
import com.infox.messagebook.utils.SysUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
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
public class FileController{
    private Logger logger = LoggerFactory.getLogger(FileController.class);
    private File base = null;

    public FileController () {
        base = new File(System.getProperty("files.rootDir","files"));
        if (!base.exists())
            base.mkdirs();
    }

    @RequestMapping(value="list")
    public @ResponseBody FileInfo list(@RequestParam(defaultValue = "") String path) {
        try {
            path = URLDecoder.decode(path,"utf-8");
            if (path.isEmpty())
                path = base.getAbsolutePath();
            FileInfo fileInfo = new FileInfo(new File(path),true,!path.equals(base.getAbsolutePath()));
            return fileInfo;
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    @RequestMapping(value="mkdir")
    public @ResponseBody JsonResponse mkdir(@RequestParam(defaultValue = "") String path,
                                            @RequestParam(defaultValue = "") String folderName,HttpServletRequest request,HttpServletResponse response) {
        try {
            path = URLDecoder.decode(path,"utf-8");
            if (path.isEmpty())
                path = base.getAbsolutePath();
            File newFile = new File(path,folderName);
            if (!newFile.exists()) newFile.mkdirs();
            return JsonResponse.SUCCESS();
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        return null;

    }

    @RequestMapping(value="delete")
    public @ResponseBody
    JsonResponse delete(@RequestParam(defaultValue = "") String path) {
        File file = new File(path);
        boolean b = file.delete();
        return new JsonResponse(JsonResponse.STATUS_SUCCESS,"success");

    }


    @RequestMapping(value="download")
    public @ResponseBody FileInfo download(@RequestParam(defaultValue = ".") String path,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {

        try {
            File file = new File(path);
            response.setContentType("text/plain");
            response.setHeader("Location",file.getName());
            response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
            OutputStream outputStream = response.getOutputStream();
            InputStream inputStream = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int i = -1;
            while ((i = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            outputStream.flush();
            outputStream.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
        }
        return null;

//        FileInfo fileInfo = new FileInfo(new File(path),true);
//        return fileInfo;
    }


    @RequestMapping(value="poll")
    public @ResponseBody
    HashMap poll(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return null;
        //return commonPoll(request,response);
    }
    @RequestMapping(value="upload")
    public @ResponseBody
    HashMap upload(@RequestParam(defaultValue = "") String path,HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (path.isEmpty())
            path = base.getAbsolutePath();
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
    //    String uploadPath = request.getServletContext().getRealPath("/") + File.separator +"uploads";


        // 如果目录不存在则创建
        File uploadDir = new File(path);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {

            final String _path = path;
            //  need define bean : org.springframework.web.multipart.commons.CommonsMultipartResolver
            if (request instanceof org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest) {
                Map<String, MultipartFile> fileMap = ((DefaultMultipartHttpServletRequest) request).getFileMap();
                fileMap.values().forEach(file-> {
                    File storeFile = getLocalFile(_path,file.getOriginalFilename());
                    try {
                        file.transferTo(storeFile);
                    } catch (IOException e) {
                        logger.error(e.getMessage(), e);
                    }
                });
            } else {
                // 解析请求的内容提取文件数据
                @SuppressWarnings("unchecked")
                List<FileItem> formItems = upload.parseRequest(request);
                List<File> storeFiles = new ArrayList<>();
                if (formItems != null && formItems.size() > 0) {
                    for (FileItem item : formItems) {
                        if (!item.isFormField()) {
                            File storeFile = getLocalFile(path,item.getName());
                            item.write(storeFile);
                            storeFiles.add(storeFile);
                        }
                    }
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

    private File getLocalFile(String path,String fileName) {
        String uploadPath = path;
        String filePath = uploadPath + File.separator + fileName;
        File storeFile = new File(filePath);
        return storeFile;
    }


}
