package za.co.standardbank.assessment1.adapter;

import org.mapstruct.Mapper;
import za.co.standardbank.assessment1.domain.model.RegisterUserRequest;
import za.co.standardbank.assessment1.persistence.entities.User;

@Mapper
public interface UserMapper {

    RegisterUserRequest dtoToModel(za.co.standardbank.assessment1.controller.model.RegisterUserRequest dto);

    User modelToEntity(RegisterUserRequest dto);

    default za.co.standardbank.assessment1.integration.model.RegisterUserRequest entityToProxy(final User entity) {
        // TODO: create RegisterUserRequest and populate fields from the user entity
        final za.co.standardbank.assessment1.integration.model.RegisterUserRequest proxy = new za.co.standardbank.assessment1.integration.model.RegisterUserRequest();
        return proxy;
    }
}
