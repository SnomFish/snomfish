package io.github.snomfish.application.profile;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.github.snomfish.application.profile.dto.summary.ProfileSummary;
import io.github.snomfish.persistence.user.User;
import io.github.snomfish.persistence.user.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProfileService {
    

    private final UserRepository userRepository;


    public ProfileSummary me(
        Authentication authentication
    ) {
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow();
        return new ProfileSummary(
            user.getUsername(),
            user.getCreatedAt()
        );
    }
}
