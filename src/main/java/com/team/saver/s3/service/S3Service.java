package com.team.saver.s3.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.team.saver.common.exception.CustomRuntimeException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

import static com.team.saver.common.dto.ErrorMessage.AWS_SERVER_EXCEPTION;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = UUID.randomUUID().toString();

        ObjectMetadata metadata= new ObjectMetadata();
        metadata.setContentType(multipartFile.getContentType());
        metadata.setContentLength(multipartFile.getSize());

        String imageUrl = "https://" + bucket + "/" + fileName;
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

}
