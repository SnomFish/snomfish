package io.github.snomfish.application.profile;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.snomfish.application.profile.dto.summary.ProfileSummary;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/users")
public class ProfileController {
    

    private final ProfileService profileService;


    @GetMapping("/me")
    public ResponseEntity<ProfileSummary> me(
        Authentication authentication
    ) {
        return ResponseEntity.ok(profileService.me(authentication));
    }
}
