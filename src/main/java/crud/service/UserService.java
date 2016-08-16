package crud.service;

import crud.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findById(long id);
    User addUser(User user);
    List<User> findAll();
    User findByName(String name);
    void delete(Long id);
    User save(User user);
    Page<User> findAllByPage(Pageable pageable);

}
