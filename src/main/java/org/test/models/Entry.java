package org.test.models;

import lombok.*;

/**
 * Created by hugh on 23/11/16.
 */
@Data
public class Entry {

    public Entry(String identity) {
        this.identity = identity;
    }

    private String identity;

    @Setter(AccessLevel.NONE)
    private Entry givingTo=null;
    private Entry receivingFrom=null;

    public void setGivingTo(Entry givingTo) {
        this.givingTo = givingTo;
        givingTo.setReceivingFrom(this);
    }
}
