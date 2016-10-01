package college.startup.controller;


import college.startup.domain.Project;
import college.startup.domain.Membership;
import college.startup.domain.User;
import college.startup.repository.MembershipRepository;
import college.startup.repository.ProjectRepository;
import college.startup.repository.UserRepository;
import college.startup.service.SecurityContextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/membership")
public class MembershipController {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final MembershipRepository membershipRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public MembershipController(ProjectRepository projectRepository,
                                MembershipRepository membershipRepository,
                                UserRepository userRepository,
                                SecurityContextService securityContextService) {
        this.projectRepository = projectRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
        this.securityContextService = securityContextService;
    }

    @RequestMapping(value = "/to/{projectId}", method = RequestMethod.POST)
    public void apply(@PathVariable("projectId") Long projectId) {
        final Project project = projectRepository.findOne(projectId);
        final User currentUser = securityContextService.currentUser();
        Membership membership;

        if (membershipRepository.findOneByUserAndProject(currentUser, project)
                .isPresent())
            throw new MembershipAlreadyFoundException();

        if (project.getUser().equals(currentUser)) {
            membership = new Membership(currentUser, project, "FOUNDER");
            project.setMembersJoined(project.getMembersJoined() + 1);
        } else
            membership = new Membership(currentUser, project, "NEW_APPLICANT");

        membershipRepository.save(membership);
    }

    @RequestMapping(value = "/to/{projectId}/add/users/{userId}", method = RequestMethod.PATCH)
    public void addMember(@PathVariable("projectId") Long projectId, @PathVariable("userId") Long userId) {
        final Project project = projectRepository.findOne(projectId);
        final User currentUser = securityContextService.currentUser();

        if (currentUser.getId() == userId) {
            throw new MembershipAlreadyFoundException();
        }

        if (project.getUser().equals(currentUser)) {
            final User user = userRepository.findOne(userId);
            final Membership membership = membershipRepository.findOneByUserAndProject(user, project)
                    .orElseThrow(MembershipNotFoundException::new);
            if (membership.getStatus().equals("MEMBER")) {
                throw new MembershipAlreadyFoundException();
            } else {
                membership.setStatus("MEMBER");
                project.setMembersJoined(project.getMembersJoined() + 1);
                membershipRepository.save(membership);
            }
        } else
            throw new UserNotOwnerException();


    }

    @RequestMapping(value = "/to/{projectId}", method = RequestMethod.DELETE)
    public void unjoin(@PathVariable("projectId") Long projectId) {
        final User currentUser = securityContextService.currentUser();
        final Project project = projectRepository.findOne(projectId);
        final Membership membership = membershipRepository
                .findOneByUserAndProject(currentUser, project)
                .orElseThrow(MembershipNotFoundException::new);

        membershipRepository.delete(membership);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User does not belong to group")
    private class MembershipNotFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Membership already found")
    private class MembershipAlreadyFoundException extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.CONFLICT, reason = "User is not the founder of the group")
    private class UserNotOwnerException extends RuntimeException {
    }

}
