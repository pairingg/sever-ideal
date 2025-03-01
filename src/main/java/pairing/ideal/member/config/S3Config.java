package pairing.ideal.member.config;

import com.amazonaws.HttpMethod;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import java.net.URL;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class S3Config {

    private final AmazonS3 s3Client;

    @Value("${cloud.ncp.storage.bucket-name-member}")
    private String bucketName;

    public S3Config(
            @Value("${cloud.ncp.storage.access-key}") String accessKey,
            @Value("${cloud.ncp.storage.secret-key}") String secretKey,
            @Value("${cloud.ncp.storage.end-point}") String endPoint,
            @Value("${cloud.ncp.storage.region-name}") String region) {

        this.s3Client = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endPoint, "kr-standard"))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    public String generatePresignedUrl(String objectKey, HttpMethod method, long expirationTimeMillis,String contentType) {
        Date expiration = new Date(System.currentTimeMillis() + expirationTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest =
                new GeneratePresignedUrlRequest(bucketName, objectKey)
                        .withMethod(method)
                        .withExpiration(expiration)
                        .withContentType(contentType); // Content-Type 추가

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }
}