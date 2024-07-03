package com.project.dailylog.board.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.project.dailylog.board.domain.Board;
import com.project.dailylog.board.domain.Image;
import com.project.dailylog.board.repository.ImageRepository;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Service
public class S3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    private final ImageRepository imageRepository;

    private String uploadFile(MultipartFile multipartFile, String s3FileName) throws IOException {
        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(multipartFile.getInputStream().available());
        amazonS3.putObject(bucket, s3FileName, multipartFile.getInputStream(), objMeta);
        return URLDecoder.decode(amazonS3.getUrl(bucket, s3FileName).toString(), "utf-8");
    }

    public void deleteFile(String keyName) {
        try {
            amazonS3.deleteObject(bucket, keyName);
        } catch (AmazonServiceException e) {
            log.error(e.toString());
        }
    }

    public String getPresignedURL (String keyName) {
        String preSignedURL = "";
        Date expiration = new Date();
        Long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2; // 2분
        expiration.setTime(expTimeMillis);

        try {
            GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, keyName)
                    .withMethod(HttpMethod.GET)
                    .withExpiration(expiration);
            URL url = amazonS3.generatePresignedUrl(generatePresignedUrlRequest);
            preSignedURL = url.toString();
        } catch (Exception e) {
            log.error(e.toString());
        }

        return preSignedURL;
    }

    public ResponseEntity<?> submitFiles (Long boardId, List<MultipartFile> multipartFileList) throws IOException {
        List<String> imageUrlList = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFileList) {
            String fileName = UUID.randomUUID() + multipartFile.getOriginalFilename();
            uploadFile(multipartFile, fileName);
            imageUrlList.add(fileName);
        }

        Board board = new Board(boardId);

        Image image = Image.builder()
                .board(board)
                .storedFileUrlList(imageUrlList)
                .build();

        imageRepository.save(image);

        return ResponseEntity.ok().build();
    }
}
