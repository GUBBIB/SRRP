package com.github.gubbib.backend.Domain.User;

import lombok.Getter;

@Getter
public enum Provider {
    LOCAL("LOCAL"),
    GOOGLE("GOOGLE");

    private final String provider;

    Provider(String provider) {
        this.provider = provider;
    }

}
