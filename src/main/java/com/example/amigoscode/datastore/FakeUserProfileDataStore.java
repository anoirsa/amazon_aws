package com.example.amigoscode.datastore;


import com.example.amigoscode.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDataStore {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.fromString("664702ef-0212-49b0-8216-f4bfc414343d"),"JanetJones",null));
        USER_PROFILES.add(new UserProfile(UUID.fromString("8f65c945-bf55-4609-ac68-545842f55a90"),"AntonioJunior",null));
    }

    public List<UserProfile> getUserProfiles() {
        return USER_PROFILES;
    }
}
