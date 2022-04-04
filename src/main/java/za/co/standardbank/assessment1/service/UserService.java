package za.co.standardbank.assessment1.service;

import za.co.standardbank.assessment1.domain.model.RegisterUserRequest;
import za.co.standardbank.assessment1.persistence.entities.User;

public interface UserService {

    Long registerUser(RegisterUserRequest request);

}
