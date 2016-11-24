package org.test.services;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.NoArgsConstructor;
import org.test.models.Entry;
import org.test.models.Group;

import java.util.Collection;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Created by hugh on 23/11/16.
 */
@NoArgsConstructor
public class SecretSantaInterface {

    Multimap<Group, Entry> groups = HashMultimap.create();

    public void addGroup(Group g) {
        groups.put(g, null);
    }

    /**
     * Remove a group from the interface and return all entries that no longer have a group
     * @param g the group to remove from the interface
     * @return the entries that no longer have a group
     */
    public Collection<Entry> removeGroup(Group g) {
        return null;
    }

    /**
     * Adds an entry to the group
     * @param g
     * @param e
     */
    public void addEntryToGroup(Group g, Entry e) {
        groups.put(g, e);
    }

    /**
     * Get all the groups that are contained within secret santa
     * @return the set of groups within secret santa
     */
    public Set<Group> getGroups() {
        return groups.keySet();
    }

    public Set<Group> getGroups(Entry e) {
        return groups.keySet().stream().filter(group -> groups.get(group).contains(e)).collect(Collectors.toSet());
    }

    public Collection<Entry> getAllEntries() {
        return groups.values().stream().filter(entry -> entry!=null).collect(Collectors.toSet());
    }

    public Collection<Entry> getEntries(Group group) {
        return groups.get(group).stream().filter(entry -> entry!=null).collect(Collectors.toSet());
    }

    public void removeEntryFromGroup(Group group, Entry entry) {
        groups.get(group).remove(entry);
    }

    public void removeEntryFromAllGroups(Entry entry) {
        // Required because the data structure isn't concurrent (only 1 iterator allowed)
        Collection<Group> allGroups = groups.keySet().stream().collect(Collectors.toList());
        for (Group g: allGroups) {
            groups.get(g).remove(entry);
        }
    }

    public Set<Entry> findEntriesMatching(Group group, Predicate<Entry> predicate) {
        return getEntries(group).stream().filter(predicate).collect(Collectors.toSet());
    }

    public Set<Entry> findAllEntriesMatching(Predicate<Entry> predicate) {
        return getAllEntries().stream().filter(predicate).collect(Collectors.toSet());
    }
}
