package pl.training.payments.adapters.commons.web;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class LocationUri {

    private static final String SEGMENT_SEPARATOR = "/";

    public static URI fromCurrentRequestWith(final Object segment) {
        return ServletUriComponentsBuilder.fromCurrentRequest()
                .path(SEGMENT_SEPARATOR + segment)
                .build()
                .toUri();
    }

}
