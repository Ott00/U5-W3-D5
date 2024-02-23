package otmankarim.U5W3D5.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public Page<User> getAllUsers(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size,
                                  @RequestParam(defaultValue = "id") String orderBy) {
        return this.userService.getUsers(page, size, orderBy);
    }

    @GetMapping("/{id}")
    public User findById(@PathVariable long id) {
        return this.userService.findById(id);
    }
}
