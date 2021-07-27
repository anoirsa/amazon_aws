package com.example.amigoscode.bucket;

public enum BucketName {
    PROFILE_IMAGE("anouar-upload");

    private final String BucketName;

    BucketName(String bucketName) {
        BucketName = bucketName;
    }

    public String getBucketName() {
        return BucketName;
    }
}
