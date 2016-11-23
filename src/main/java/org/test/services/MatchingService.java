package org.test.services;

import lombok.RequiredArgsConstructor;
import org.test.models.Entry;

import java.util.Collection;

/**
 * Created by hugh on 23/11/16.
 */
@RequiredArgsConstructor
public class MatchingService {

    private final SecretSantaInterface secretSantaInterface;

    private final double chanceOfOutOfGroup;
    private final double chanceOfOutOfFriends;

    public Collection<Pair> findPairs() {
        /*
        TODO
        While not all entries set
        find pair matching entry
        set buyer
        continue
         */
        return null;
    }

    private static class Pair {
        Entry giver;
        Entry receiver;
    }
}
