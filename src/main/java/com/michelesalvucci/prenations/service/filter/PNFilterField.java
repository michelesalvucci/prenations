package com.michelesalvucci.prenations.service.filter;

import java.io.Serializable;

import lombok.Data;

@Data
public class PNFilterField<T> implements Serializable {

    private T value;
    private String operation;
}
