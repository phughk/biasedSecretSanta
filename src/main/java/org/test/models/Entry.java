package org.test.models;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by hugh on 23/11/16.
 */
@Data
public class Entry {

    private String identity;

    public Entry(String identifier) {
        identity = identifier;
    }

    private final Set<SecretSet> groups = new HashSet<>();

    public void addToSet(SecretSet set) {
        groups.add(set);
        set.add(this);
    }

    public void removeFromSet(SecretSet set) {
        groups.remove(set);
        set.remove(this);
    }
}
