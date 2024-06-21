package com.team.saver.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.team.saver.common.dto.ErrorMessage.AWS_SERVER_EXCEPTION;
import static com.team.saver.common.dto.ErrorMessage.NOT_IMAGE_FILE;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public List<String> uploadMultipleImage(List<MultipartFile> multipartFileList) {
        List<String> result = new ArrayList<>();

        for(MultipartFile multipartFile : multipartFileList) {
            result.add(uploadImage(multipartFile));
        }

        return result;
    }

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = multipartFile.getOriginalFilename();
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        if(extension.equals("jpg") || extension.equals("png") || extension.equals("jpeg")) {
            return uploadFile(multipartFile);
        }

        throw new CustomRuntimeException(NOT_IMAGE_FILE);
    }

    private String uploadFile(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        String imageUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket ,region , fileName);
        try{
            amazonS3Client.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new CustomRuntimeException(AWS_SERVER_EXCEPTION, e.getMessage());
        }

        return imageUrl;
    }

    public void deleteImage(String fileName) {
        amazonS3Client.deleteObject(bucket, fileName);
    }

    public void deleteMultipleImage(List<String> fileNameList) {
        for(String fileName : fileNameList) {
            deleteImage(fileName);
        }
    }

    private String getObjectNameByUri(String imageUrl) {
        String url[] = imageUrl.split("/");

        return url[url.length - 1];
    }

}
