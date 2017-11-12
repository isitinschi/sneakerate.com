package com.sneakerate.util.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class S3Service<T> {
    private static final String AWS_ACCESS_KEY_ID = "your_access_key_id";
    private static final String AWS_SECRET_ACCESS_KEY = "your_secret_access_key";

    private AmazonS3 s3;

    ObjectMapper mapper = new ObjectMapper();

    private static final Logger LOGGER = LoggerFactory.getLogger(S3Service.class);

    public S3Service() {
        AWSCredentials credentials = new BasicAWSCredentials(AWS_ACCESS_KEY_ID, AWS_SECRET_ACCESS_KEY);

        s3 = new AmazonS3Client(credentials);

        Region usWest2 = Region.getRegion(Regions.US_WEST_2);
        s3.setRegion(usWest2);
    }

    public void upload(String key, Collection<T> specialOffers) throws IOException {
        PutObjectRequest request = new PutObjectRequest("sneakerate", key, saveToFile(specialOffers, key));
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(request);
        new File(key).delete();
    }

    private File saveToFile(Collection<T> specialOffers, String name) throws IOException {
        File file = new File(name);
        file.createNewFile();

        mapper.writeValue(file, specialOffers);

        return file;
    }

    public void upload(String key, BufferedImage image) throws IOException {
        PutObjectRequest request = new PutObjectRequest("sneakerate", key, saveToFile(image, key));
        request.setCannedAcl(CannedAccessControlList.PublicRead);
        s3.putObject(request);
        new File(key).delete();
    }

    private File saveToFile(BufferedImage img, String name) throws IOException {
        File file = new File(name);
        file.createNewFile();

        ImageIO.write(img, "jpg", file);

        return file;
    }

    public List<T> downloadSpecialOffers(Class<T> clazz) {
        try {
            InputStream is = s3.getObject(new GetObjectRequest("sneakerate", "special-offers.json")).getObjectContent();
            return mapper.readValue(is, mapper.getTypeFactory().constructParametrizedType(List.class, List.class, clazz));
        } catch (Exception e) {
            LOGGER.error("Error while downloading special-offers.json", e);
        }

        return Collections.EMPTY_LIST;
    }
}
