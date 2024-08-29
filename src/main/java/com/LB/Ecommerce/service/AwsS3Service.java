package com.LB.Ecommerce.service;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

import lombok.extern.slf4j.Slf4j;



@Service
@Slf4j
public class AwsS3Service {

    private final String bucketName="lb-ecommerce";

    @Value("${aws.s3.access}")
    private String awsS3AccessKey;
    @Value("${aws.s3.secrete}")
    private String awsS3SecreteKey;

    
    public String saveImageToS3(MultipartFile photo){
        try {
            String s3FileName =photo.getOriginalFilename();

            //Create aes credentials using the access and secrete key
            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(awsS3AccessKey, awsS3SecreteKey);
            
            //Create an s3 client with config credentials and region
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.US_EAST_2)
                .build();
            

            //Get input stream from photo
            InputStream inputStream = photo.getInputStream();

            //Set metadata for the object
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("image/jpeg");

            //Create a put request to upload the image to s3
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName,s3FileName, inputStream, metadata);
            s3Client.putObject(putObjectRequest);
            
            return "http://" + bucketName + ".s3.ap-southeast-2.amazonaws.com/" + s3FileName;

        } catch (IOException e) {
            e.printStackTrace();
            throw  new RuntimeException("Unable to upload image to s3 bucket: " + e.getMessage());
        }
    }
    
    
}
