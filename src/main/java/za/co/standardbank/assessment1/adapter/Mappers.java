package za.co.standardbank.assessment1.adapter;

import static org.mapstruct.factory.Mappers.getMapper;

public final class Mappers {

    public static final UserMapper USER = getMapper(UserMapper.class);

    private Mappers() {}
}
