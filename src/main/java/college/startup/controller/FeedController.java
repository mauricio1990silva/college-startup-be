package college.startup.controller;

import college.startup.dto.PageParams;
import college.startup.dto.ProjectPostDTO;
import college.startup.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/feed")
public class FeedController {

    @SuppressWarnings("UnusedDeclaration")
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    private final ProjectService projectService;

    @Autowired
    public FeedController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping
    public List<ProjectPostDTO> feed(PageParams pageParams) {
        return projectService.findAsFeed(pageParams);
    }

    @RequestMapping(value = "/public", method = RequestMethod.GET)
    public List<ProjectPostDTO> publicFeed(PageParams pageParams) {
        return projectService.findAsPublicFeed(pageParams);
    }

}
