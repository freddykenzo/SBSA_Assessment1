package za.co.standardbank.assessment1.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.standardbank.assessment1.adapter.Mappers;
import za.co.standardbank.assessment1.controller.model.RegisterUserRequest;
import za.co.standardbank.assessment1.service.UserService;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController implements UserApi {

    private final UserService service;

    @Override
    public ResponseEntity<Long> registerUser(final RegisterUserRequest registerUserRequest) {
        return ResponseEntity.ok(service.registerUser(Mappers.USER.dtoToModel(registerUserRequest)));
    }
}
