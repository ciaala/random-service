package com.ffi.service.randomGenerator;

import java.util.Date;

/**
 * Created by cryptq on 9/14/16.
 */
class RandomResult {
    public final Date created;
    public final String value;
    public int views;
    public final String identifier;

    public RandomResult(String identifier, String value) {
        this.identifier = identifier;
        this.created = new Date();
        this.value = value;
        this.views = 0;
    }


}
