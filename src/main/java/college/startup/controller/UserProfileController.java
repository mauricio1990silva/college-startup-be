package college.startup.controller;


import college.startup.auth.TokenHandler;
import college.startup.domain.Tag;
import college.startup.domain.User;
import college.startup.domain.UserProfile;
import college.startup.dto.ErrorResponse;
import college.startup.dto.UserDTO;
import college.startup.dto.UserParams;
import college.startup.dto.UserProfileParams;
import college.startup.repository.TagRepository;
import college.startup.repository.UserProfileRepository;
import college.startup.repository.UserRepository;
import college.startup.service.SecurityContextService;
import college.startup.service.UserService;
import college.startup.util.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Nullable;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/users/profile")
public class UserProfileController {

    private final UserProfileRepository userProfileRepository;
    private final TagRepository tagRepository;
    private final UserService userService;
    private final SecurityContextService securityContextService;

    @Autowired
    public UserProfileController(UserProfileRepository userProfileRepository,
                                 TagRepository tagRepository,
                                 UserService userService,
                                 SecurityContextService securityContextService) {
        this.userProfileRepository = userProfileRepository;
        this.tagRepository = tagRepository;
        this.userService = userService;
        this.securityContextService = securityContextService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public UserProfile create(@Valid @RequestBody UserProfileParams params) {
        final User user = securityContextService.currentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }
        final Set<Tag> tags = RepositoryUtil.removeDuplicates(tagRepository, params.getProjectTags().get());
        params.setUser(user);
        params.setTags(tags);
        return userProfileRepository.save(params.toUserProfile());
    }


    @RequestMapping(value = "/me", method = RequestMethod.PATCH)
    public UserProfile updateMe(@Valid @RequestBody UserProfileParams params) {
        final User user = securityContextService.currentUser();
        if (user == null) {
            throw new UserNotFoundException();
        }
        params.getMajor().ifPresent(user.getUserProfile()::setMajor);
        params.getYear().ifPresent(user.getUserProfile()::setYear);

        final Set<Tag> tags = RepositoryUtil.removeDuplicates(tagRepository, params.getProjectTags().get());
        user.getUserProfile().setTags(tags);
        return userProfileRepository.save(user.getUserProfile());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No user")
    private class UserNotFoundException extends RuntimeException {
    }
}
