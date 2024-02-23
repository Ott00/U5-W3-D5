package otmankarim.U5W3D5.user;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import otmankarim.U5W3D5.exceptions.BadRequestException;
import otmankarim.U5W3D5.exceptions.NotFoundException;

@Service
public class UserService {
    @Autowired
    private UserDAO userDAO;

    public Page<User> getUsers(int pageNumber, int size, String orderBy) {
        if (size > 100) size = 100;
        Pageable pageable = PageRequest.of(pageNumber, size, Sort.by(orderBy));
        return userDAO.findAll(pageable);
    }

    public User save(UserDTO newUser) {
        userDAO.findByEmail(newUser.email()).ifPresent(author -> {
            throw new BadRequestException("L'email " + newUser.email() + " è già in uso!");
        });
        User user = new User(
                newUser.name(),
                newUser.surname(),
                newUser.email(),
                newUser.password(),
                newUser.role()
        );
        return userDAO.save(user);
    }

    public User findById(long id) {
        return userDAO.findById(id).orElseThrow(() -> new NotFoundException(id));
    }
}
