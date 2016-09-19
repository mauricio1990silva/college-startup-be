package college.startup.service;


import college.startup.domain.Project;
import college.startup.domain.Tag;
import college.startup.domain.User;
import college.startup.dto.PageParams;
import college.startup.dto.ProjectParams;
import college.startup.dto.ProjectPostDTO;
import college.startup.repository.ProjectRepository;
import college.startup.repository.TagRepository;
import college.startup.service.exception.NotPermittedException;
import college.startup.util.RepositoryUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final TagRepository tagRepository;
    private final SecurityContextService securityContextService;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository,
                              TagRepository tagRepository,
                              SecurityContextService securityContextService) {
        this.projectRepository = projectRepository;
        this.tagRepository = tagRepository;
        this.securityContextService = securityContextService;
    }

    @Override
    public void delete(Long id) {
        User currentUser = securityContextService.currentUser();
        Project project = projectRepository.findOne(id);
        if (currentUser != project.getUser())
            throw new NotPermittedException("no permission to delete this post");
        projectRepository.delete(id);
    }

    @Override
    public Project update(Project project, ProjectParams projectParams) {

        projectParams.getName().ifPresent(project::setName);
        projectParams.getType().ifPresent(project::setType);
        projectParams.getContent().ifPresent(project::setContent);
        projectParams.getMembersRequired().ifPresent(project::setMembersRequired);

        Set<Tag> tags = RepositoryUtil.removeDuplicates(tagRepository, projectParams.getProjectTags().get());
        project.setTags(tags);

        return projectRepository.save(project);
    }

    @Override
    public List<ProjectPostDTO> findAsFeed(PageParams pageParams) {
        final User currentUser = securityContextService.currentUser();
        final List<ProjectPostDTO> feed = projectRepository.findAsFeed(currentUser, pageParams);
        feed.forEach(p -> p.setIsMyProject(p.getUser().getId() == currentUser.getId()));
        return feed;
    }

    @Override
    public List<ProjectPostDTO> findAsPublicFeed(PageParams pageParams) {
        final User currentUser = securityContextService.currentUser();
        final List<ProjectPostDTO> feed = projectRepository.findAsPublicFeed(currentUser, pageParams);
        feed.forEach(p -> p.setIsMyProject(p.getUser().getId() == currentUser.getId()));
        return feed;
    }

    @Override
    public List<ProjectPostDTO> findByUser(User user, PageParams pageParams) {
        final User currentUser = securityContextService.currentUser();
        final Boolean isMyPost = (currentUser != null) ? (currentUser.equals(user)) : null;
        return projectRepository.findByUser(user, pageParams)
                .stream()
                .map(p -> ProjectPostDTO.builder()
                                .project(p)
                                .user(p.getUser())
                                .isMyProject(isMyPost)
                                .build()
                )
                .collect(Collectors.toList());
    }

}
