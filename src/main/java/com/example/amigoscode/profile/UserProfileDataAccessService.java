package com.example.amigoscode.profile;


import com.example.amigoscode.datastore.FakeUserProfileDataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserProfileDataAccessService {

    private final FakeUserProfileDataStore fakeUserProfileDataStore;


    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDataStore fakeUserProfileDataStore) {
        this.fakeUserProfileDataStore = fakeUserProfileDataStore;
    }

    public List<UserProfile> getUserProfiles() {
        return  fakeUserProfileDataStore.getUserProfiles();
    }
}
