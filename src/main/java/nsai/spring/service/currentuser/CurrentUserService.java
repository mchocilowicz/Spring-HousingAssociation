package nsai.spring.service.currentuser;

import nsai.spring.domain.CurrentUser;

public interface CurrentUserService {

    boolean canAccessUser(CurrentUser currentUser, Long userId);

}
