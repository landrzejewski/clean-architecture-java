package pl.training.commons.model;

import java.util.List;

public record ResultPage<T>(List<T> content, PageSpec pageSpec, int totalPages) {
}
