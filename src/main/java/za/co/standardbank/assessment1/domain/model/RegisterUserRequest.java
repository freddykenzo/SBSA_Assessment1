package za.co.standardbank.assessment1.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {

    private String firstname;

    private String surname;

    private String mobileNumber;

    private String email;
}
