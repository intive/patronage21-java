package com.intive.patronative.mapper;

import java.util.Optional;

class Mapper {

    protected String swapValues(final String newValue, final String oldValue) {
        return Optional.ofNullable(newValue).orElse(oldValue);
    }

}