package nsai.spring.service.user;


import nsai.spring.domain.User;
import nsai.spring.domain.UserCreateForm;

import java.util.Collection;
import java.util.Optional;

public interface UserService {

    Optional<User> getUserById(long id);

    Optional<User> getUserByEmail(String email);

    Collection<User> getAllUsers();

    User create(UserCreateForm form);

    void delete(Long id);

    void update(User user);

}
