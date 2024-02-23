package otmankarim.U5W3D5.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import otmankarim.U5W3D5.auth.DTO.UserLoginDTO;
import otmankarim.U5W3D5.auth.JWT.JWTTools;
import otmankarim.U5W3D5.exceptions.UnauthorizedException;
import otmankarim.U5W3D5.user.User;
import otmankarim.U5W3D5.user.UserService;

@Service
public class AuthService {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder bcrypt;
    @Autowired
    private JWTTools jwtTools;

    public String authenticateUserAndGenerateToken(UserLoginDTO payload) {
        User user = userService.findByEmail(payload.email());
        if (bcrypt.matches(payload.password(), user.getPassword())) {
            return jwtTools.createToken(user);
        } else {
            throw new UnauthorizedException("Wrong credentials!");
        }
    }
}
