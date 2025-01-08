package com.blog.services.impl;

import com.blog.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileSystemImpl implements FileService {

    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {

        // file name
        String name = file.getOriginalFilename();

        // random file name generator
        String randomID = UUID.randomUUID().toString();
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));
        String filePath = path + File.separator + fileName1;

        // create folder if needed
        File f = new File(path);
        if (!f.exists()) {
            f.mkdir();
        }

        // copy file
        Files.copy(file.getInputStream(), Paths.get(filePath));
        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path + File.separator + fileName;
        InputStream is = new FileInputStream(fullPath);
        return is;
    }

    @Override
    public boolean deleteFile(String path, String fileName) throws IOException {
        path = path.endsWith(File.separator) ? path : path + File.separator;

        File file = new File(path + fileName);
        if (file.exists()) {
            return file.delete();
        }
        return false;
    }
}
