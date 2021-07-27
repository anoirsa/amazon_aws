package com.example.amigoscode.profile;


import com.amazonaws.services.dynamodbv2.xspec.S;
import com.example.amigoscode.bucket.BucketName;
import com.example.amigoscode.filestore.FileStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;

    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = fileStore;
    }

    List<UserProfile> getUserProfiles() {
        return userProfileDataAccessService.getUserProfiles();
    }

    public void uploadUserProfileImage(UUID userProfileId, MultipartFile file)  {
        // 1. Check if image is not empty
        // 2. If file is an image
        // 3. The user exists in our database
        // 4. Grab some metadata from file if any
        // 5. Store the image in s3 update database with s3 image link
        if (file.isEmpty()) throw new IllegalStateException("File is empty");
        if (!file.getContentType().contains("image")) throw new IllegalStateException("File is not an image");
        UserProfile user = getUserProfileOrThrow(userProfileId);

        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());
        try {
            InputStream inputStream = file.getInputStream();
            fileStore.save(path, filename, Optional.ofNullable(metadata) ,inputStream);
            user.setUserProfileImageLink(filename);
        } catch (IOException exception) {
            System.out.println("File problem exception");
        }


    }


    public byte[] downloadUserProfileImage(UUID userProfileId) {
        UserProfile user = getUserProfileOrThrow(userProfileId);
        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),user.getUserProfileId());
        return user.getUserProfileImageLink().map(key -> fileStore.download(path, key)).orElse(new byte[9]);

    }


    private UserProfile getUserProfileOrThrow(UUID userProfileId) {
        return userProfileDataAccessService.getUserProfiles()
                .stream().filter(userProfile -> userProfile.getUserProfileId()
                        .equals(userProfileId)).findAny().orElseThrow(() -> new IllegalStateException("User not in our database"));
    }

}
