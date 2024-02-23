package otmankarim.U5W3D5.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserDTO(
        @NotEmpty(message = "name is mandatory")
        @Size(min = 3, message = "name must be at least 3 characters")
        String name,
        @NotEmpty(message = "surname is mandatory")
        @Size(min = 3, message = "surname must be at least 3 characters")
        String surname,
        @NotEmpty(message = "email is mandatory")
        @Email(message = "email is not valid")
        String email,
        @NotEmpty(message = "password is mandatory")
        @Size(min = 8, message = "name must be at least 8 characters")
        String password,
        @NotEmpty(message = "role is mandatory [NORMAL_USER, EVENT_ORGANIZER]")
        @NotNull
        Role role

) {
}
