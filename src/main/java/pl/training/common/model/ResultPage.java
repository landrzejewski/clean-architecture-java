package pl.training.common.model;

import java.util.List;
import java.util.function.Function;

public record ResultPage<T>(List<T> content, PageSpec pageSpec, int totalPages) {

    public <O> ResultPage<O> map(Function<T, O> mapper) {
        return new ResultPage<>(content.stream().map(mapper).toList(), pageSpec, totalPages);
    }

}
