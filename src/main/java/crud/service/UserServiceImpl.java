package crud.service;

import crud.model.User;
import crud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("UserService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User addUser(User user) {

        return repository.saveAndFlush(user);
    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public User findByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }

    public User findById(long id){
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAllByPage(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
