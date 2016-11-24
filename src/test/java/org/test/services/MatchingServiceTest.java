package org.test.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.test.models.Entry;
import org.test.models.Group;

import java.util.Arrays;
import java.util.List;

/**
 * Created by phkaz on 24/11/2016.
 */
public class MatchingServiceTest {

    private MatchingService subject;
    private SecretSantaInterface secretSantaService;

    @Before
    public void init() {
        secretSantaService = new SecretSantaInterface();
        subject = new MatchingService(secretSantaService);
    }

    @Test
    public void something() {

        //given
        simpleFamily(secretSantaService);

        // when
        Object o = subject.findPairs();

        // then
        Assert.assertEquals(o, new Object());
    }

    private static void simpleFamily(SecretSantaInterface secretSantaInterface) {
        EntryFactory factory = entryFactoryConstructor(secretSantaInterface);
        Group familyA = new Group("Family A");
        Group familyB = new Group("Family B");
        Group kids = new Group("Kids");
        Group parents = new Group("Parents");

        factory.newLinkedEntry("Dad A", familyA, parents);
        factory.newLinkedEntry("Mum A", familyA, parents);
        factory.newLinkedEntry("Boy A", familyA, kids);
        factory.newLinkedEntry("Girl A", familyA, kids);

        factory.newLinkedEntry("Dad B", familyB, parents);
        factory.newLinkedEntry("Mum B", familyB, parents);
        factory.newLinkedEntry("Oldest B", familyB, kids);
        factory.newLinkedEntry("Middle B", familyB, kids);
        factory.newLinkedEntry("Youngest B", familyB, kids);
    }

    private interface EntryFactory {
        Entry newLinkedEntry(String identity, Group... groups);
    }

    private static EntryFactory entryFactoryConstructor(SecretSantaInterface secretSantaInterface) {
        return new EntryFactory() {
            @Override
            public Entry newLinkedEntry(String identity, Group... groups) {
                Entry entry = new Entry(identity);
                for (Group g: groups) {
                    secretSantaInterface.addEntryToGroup(g, entry);
                }
                return entry;
            }
        };
    }
}
