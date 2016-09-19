package college.startup.service;


import college.startup.domain.User;

public interface SecurityContextService {
    User currentUser();
}
