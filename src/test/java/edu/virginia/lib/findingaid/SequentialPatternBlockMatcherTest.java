package edu.virginia.lib.findingaid;

import edu.virginia.lib.findingaid.rules.BlockMatch;
import edu.virginia.lib.findingaid.rules.ElementPattern;
import edu.virginia.lib.findingaid.rules.SequentialPatternBlockMatcher;
import edu.virginia.lib.findingaid.structure.Element;
import edu.virginia.lib.findingaid.structure.Schema;
import edu.virginia.lib.findingaid.structure.XmlSerializedSchema;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class SequentialPatternBlockMatcherTest {

    private Schema s;

    @Before
    public void setup() throws JAXBException {
        s = XmlSerializedSchema.loadSchema(getClass().getClassLoader().getResourceAsStream("test-schema-1.xml"));
    }

    @Test
    public void testMatchAll() {
        SequentialPatternBlockMatcher m = new SequentialPatternBlockMatcher(Arrays.asList(new ElementPattern[] {new ElementPattern() {
            @Override
            public String getName() {
                return "Match all";
            }

            @Override
            public Pattern getPattern() {
                return Pattern.compile(".*");
            }

            @Override
            public boolean matchesMultiple() {
                return true;
            }
        }} ));

        List<BlockMatch> matches = m.findMatches(createDummyElementsWithContent("one", "two", "three"));
        Assert.assertEquals(1, matches.size());
        Assert.assertEquals(3, matches.get(0).getMatches().size());

    }

    private Element createDummyElementsWithContent(final String ... contents) {
        Element el = new Element(s.getRootNodeType());
        ArrayList<Element> result = new ArrayList<Element>();
        for (String c : contents) {
            el.addChild(new Element(s.getUnassignedType(), c));
        }
        return el;
    }
}