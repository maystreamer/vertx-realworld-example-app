package io.greyseal.realworld.util;

        import org.modelmapper.ModelMapper;
        import org.modelmapper.convention.MatchingStrategies;

public class MapperUtil {

    private static ModelMapper mapper;

    static {
        mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    }

    public static <T> T map(Object source, Class<T> destinationType) {
        return mapper.map(source, destinationType);
    }
}