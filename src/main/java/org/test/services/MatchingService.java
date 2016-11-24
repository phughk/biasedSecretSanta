package org.test.services;

import lombok.RequiredArgsConstructor;
import org.test.models.Entry;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by hugh on 23/11/16.
 */
@RequiredArgsConstructor
public class MatchingService {

    private final SecretSantaInterface secretSantaInterface;

    private final Predicate<Entry> areNonBuying = entry -> entry.getGivingTo()==null;

    private final Predicate<Entry> areNonReceiving = entry -> entry.getReceivingFrom()==null;

    public Collection<Pair> findPairs() {
        Set<Entry> notBuying = secretSantaInterface.findAllEntriesMatching(areNonBuying);
        Set<Entry> notGetting = secretSantaInterface.findAllEntriesMatching(areNonReceiving);
        while (notBuying.size()>0 && notGetting.size()>0) {

        }
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
        public Pair(Entry giver) {
            this.giver = giver;
            this.receiver = giver.getReceivingFrom();
        }
    }

}
