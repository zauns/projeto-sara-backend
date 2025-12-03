package sara.projeto.saraEmprega.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.services.s3.S3Client;
import java.util.UUID;

import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
@RequiredArgsConstructor
public class R2Service {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucketName;

    public String upload(UUID userId, String tipo, MultipartFile file,String fileName) throws IOException {

        String key = generateKey(tipo,userId,fileName);
        Path tempFile = Files.createTempFile("upload-", fileName);
        file.transferTo(tempFile.toFile());

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .contentType("application/pdf")
                        .build(),
                tempFile
        );

        Files.deleteIfExists(tempFile);
        return key;
    }

    public String replace(String existingKey, UUID userId, String tipo, MultipartFile file, String fileName) throws IOException {

        Path tempFile = Files.createTempFile("upload-", fileName);
        file.transferTo(tempFile.toFile());

        s3Client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(existingKey)
                        .contentType("application/pdf")
                        .build(),
                tempFile
        );

        Files.deleteIfExists(tempFile);
        return existingKey;
    }

    private String generateKey(String tipo,UUID userId,String fileName) {
        return String.format("%s/%s/%s-%s",
                tipo.toLowerCase(),
                userId.toString(),
                UUID.randomUUID(),
                fileName);
    }

    public byte[] download(String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);
            return objectBytes.asByteArray();
        }
    }
