package otmankarim.U5W3D5.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import otmankarim.U5W3D5.auth.DTO.LoginResponseDTO;
import otmankarim.U5W3D5.auth.DTO.UserLoginDTO;
import otmankarim.U5W3D5.auth.service.AuthService;
import otmankarim.U5W3D5.user.User;
import otmankarim.U5W3D5.user.UserDTO;
import otmankarim.U5W3D5.user.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody UserLoginDTO payload) {
        return new LoginResponseDTO(authService.authenticateUserAndGenerateToken(payload));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED) // 201
    public User register(@RequestBody UserDTO newUser) {
        return this.userService.save(newUser);
    }
}
