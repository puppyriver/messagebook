package com.infox.messagebook.services;

import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class ImageService {
    private File thumbnails = new File("thumbnails");
    public ImageService() {
        if (!thumbnails.exists()) {
            thumbnails.mkdirs();
        }
    }

    public void createThumbnail (File file) throws IOException {
        Thumbnails.of(file).size(200,200)
//                .outputFormat("jpg")
                .toFile(new File(thumbnails,file.getName()));

    }
    public FileInputStream getThumbnail(File file) throws IOException {
        File tb;
        if ((tb = new File(thumbnails,file.getName())).exists()) {
            return new FileInputStream(tb);
        } else {
            createThumbnail(file);
            tb = new File(thumbnails,file.getName());
            return new FileInputStream(tb);
        }
    }

    public static void main(String[] args) throws IOException {
        new ImageService().createThumbnail(new File("d:\\aaa.png"));
    }
}
