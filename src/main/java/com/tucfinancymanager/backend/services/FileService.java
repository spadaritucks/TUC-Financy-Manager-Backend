package com.tucfinancymanager.backend.services;

import com.amazonaws.services.s3.AmazonS3;
import com.tucfinancymanager.backend.exceptions.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    @Autowired
    private AmazonS3 S3Client;

    @Value("${aws.bucket.name}")
    private String bucketName;


    public String uploadImage(MultipartFile multipartFile) {
        String filename = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();

        try {
            File file = this.convertMultipartFile(multipartFile);
            S3Client.putObject(bucketName, filename, file);
            file.delete();
            return S3Client.getUrl(bucketName, filename).toString();
        } catch (Exception e) {
            throw new FileUploadException("Erro ao realizar o upload do arquivo :" + e);
        }
    }

    private File convertMultipartFile(MultipartFile multipartFile) throws IOException {
        File convFile = new File(multipartFile.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(multipartFile.getBytes());
        fos.close();

        return convFile;
    }
}
