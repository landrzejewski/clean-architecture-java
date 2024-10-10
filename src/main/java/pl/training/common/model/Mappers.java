package pl.training.common.model;

import java.util.List;
import java.util.function.Function;

public interface Mappers {

    static <I, O> List<O> mapList(List<I> input, Function<I, O> mapper) {
        return input.stream().map(mapper).toList();
    }

}
