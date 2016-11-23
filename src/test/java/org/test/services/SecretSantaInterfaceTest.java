package org.test.services;

import com.google.common.collect.Sets;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.models.Entry;
import org.test.models.Group;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hugh on 23/11/16.
 */
public class SecretSantaInterfaceTest {

    private SecretSantaInterface subject;

    @Before
    public void init() {
        subject = new SecretSantaInterface();
    }

    @Test
    public void addedGroupsCanBeRetrieved() {

        // given
        Group group = new Group("MyGroup");

        // when
        subject.addGroup(group);

        // then
        Assert.assertEquals(Sets.newHashSet(group), subject.getGroups());
        Assert.assertThat(subject.getAllEntries(), thereAreThisManyEntries(0)); // can compare to new HashSet, but PoC
    }

    @Test
    public void addingAnEntryToAnExistingGroupCanBeRetrieved() {

        // given
        Group group = new Group("MyGroup");
        Entry entry = new Entry("My dude");

        // when
        subject.addGroup(group);
        subject.addEntryToGroup(group, entry);

        // then
        Assert.assertEquals(Sets.newHashSet(group), subject.getGroups());
        Assert.assertEquals(Sets.newHashSet(entry), subject.getEntries(group));
    }

    @Test
    public void addingAnEntryToTwoGroupsReturnsEntryOnlyOnce() {

        // given
        Group group1 = new Group("Group 1");
        Group group2 = new Group("Group 2");
        Set<Group> groupSet = Sets.newHashSet(group1, group2);
        Entry entry = new Entry("My dude");
        Set<Entry> entrySet = Sets.newHashSet(entry);

        // when
        subject.addGroup(group1);
        subject.addGroup(group2);
        subject.addEntryToGroup(group1, entry);
        subject.addEntryToGroup(group2, entry);

        // then
        Assert.assertEquals(groupSet, subject.getGroups());
        Assert.assertEquals(entrySet, subject.getEntries(group1));
        Assert.assertEquals(entrySet, subject.getEntries(group2));
        Assert.assertEquals(entrySet, subject.getAllEntries());
    }

    @Test
    public void removingAnEntryFromOneGroupDoesntRemoveFromAnother() {

        //given
        Group removeGroup = new Group("Group 1");
        Group group2 = new Group("Group 2");
        Set<Group> groupSet = Sets.newHashSet(removeGroup, group2);
        Entry entry = new Entry("My bro");
        Set<Entry> entrySet = Sets.newHashSet(entry);

        //when
        subject.addGroup(removeGroup);
        subject.addGroup(group2);
        subject.addEntryToGroup(removeGroup, entry);
        subject.addEntryToGroup(group2, entry);
        subject.removeEntryFromGroup(removeGroup, entry);

        // then
        Assert.assertEquals(groupSet, subject.getGroups());
        Assert.assertEquals(entrySet, subject.getAllEntries());
        Assert.assertEquals(new HashSet<>(), subject.getEntries(removeGroup));
        Assert.assertEquals(entrySet, subject.getEntries(group2));
    }

    @Test
    public void removingEntryFromAllGroupsRemovesTheEntryEntirely() {

        // given
        Group group1 = new Group("Group 1");
        Group group2 = new Group("Group 2");
        Entry entry = new Entry("My bro");
        Entry entry2 = new Entry("Remains");

        // when
        subject.addEntryToGroup(group1, entry);
        subject.addEntryToGroup(group1, entry2);
        subject.addEntryToGroup(group2, entry);
        subject.addEntryToGroup(group2, entry2);
        subject.removeEntryFromAllGroups(entry);

        // then
        Assert.assertEquals(Sets.newHashSet(entry2), subject.getAllEntries());
        Assert.assertEquals(Sets.newHashSet(group1, group2), subject.getGroups());
    }

    // End tests

    private Matcher<Collection<Entry>> thereAreThisManyEntries(int number) {
        return new BaseMatcher<Collection<Entry>>() {
            @Override
            public boolean matches(Object item) {
                Collection<Entry> actual = (Collection<Entry>) item;
                return actual.size() == number;
            }

            @Override
            public void describeTo(Description description) {
                description.appendText(String.format("there should be %s entries", number));
            }
        };
    }

}
