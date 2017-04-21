package com.infox.messagebook.api;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2017/4/20.
 */
public class FileInfo implements Serializable {
    private File file;
    private boolean isDirectory;
    private List<FileInfo> subFiles;
    private String name;
    private Date time;
    private long size;
    private String absolutePath;

    public FileInfo() {

    }
    public FileInfo(File file,boolean subFiles) {
        this(file,subFiles,true);
    }

    public FileInfo(File file,boolean subFiles,boolean containsParent) {
        this.file = file;

        if (file.exists()) {
            this.isDirectory = file.isDirectory();
            this.name = file.getName();
            this.time = new Date(file.lastModified());
            this.size = file.length();
            this.absolutePath = file.getAbsolutePath();
            if (subFiles && file.isDirectory()) {
                this.subFiles = Arrays.asList(file.listFiles()).stream()
                        .map(f -> new FileInfo(f,false))
                        .collect(Collectors.toList());
                this.subFiles.sort((c1,c2)-> c1.isDirectory ? -1 : 1);
                if (containsParent) {
                    FileInfo parent = new FileInfo(file.getParentFile(), false);
                    parent.setName("..");
                    this.subFiles.add(0, parent);
                }
            }

        }
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public boolean isDirectory() {
        return isDirectory;
    }

    public void setDirectory(boolean directory) {
        isDirectory = directory;
    }

    public List<FileInfo> getSubFiles() {
        return subFiles;
    }

    public void setSubFiles(List<FileInfo> subFiles) {
        this.subFiles = subFiles;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
