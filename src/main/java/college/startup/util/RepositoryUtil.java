package college.startup.util;

import college.startup.domain.Tag;
import college.startup.repository.TagRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * Created by mauriciosilva on 9/18/16.
 */
public class RepositoryUtil {

    public static Set<Tag> removeDuplicates(TagRepository tagRepository, List<String> projectTags) {
        Set<Tag> tags = new HashSet<>();

        for (String rawTag : projectTags) {
            Optional<Tag> optionalTag = tagRepository.findOneByContent(rawTag);
            Tag tag;
            if (optionalTag.isPresent()) {
                tag = optionalTag.get();
            } else {
                tag = tagRepository.save(new Tag(rawTag));
            }
            tags.add(tag);
        }

        return tags;
    }
}
