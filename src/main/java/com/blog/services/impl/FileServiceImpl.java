package com.blog.services.impl;

import com.blog.exceptions.ResourceNotProvidedException;
import com.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException, ResourceNotProvidedException {

        if(file==null){
            throw new ResourceNotProvidedException("image");
        }

        String filename = file.getOriginalFilename();

        String randomID= UUID.randomUUID().toString();
        String newFileName=randomID.concat(filename.substring(filename.lastIndexOf(".")));

        String filePath=path+File.separator+newFileName;

        File f=new File(path);
        if(!f.exists()){
            boolean mkdirs = f.mkdirs();
        }

        Files.copy(file.getInputStream(),Paths.get(filePath));

        return newFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws IOException {
        String fullPath=path+ File.separator+fileName;
        return Files.newInputStream(Paths.get(fullPath));
    }
}
