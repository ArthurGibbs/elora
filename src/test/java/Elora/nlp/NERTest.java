package Elora.nlp;

import opennlp.tools.util.Span;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NERTest {

    @Test
    @DisplayName("Simple multiplication should work")
    public void canTagName() throws IOException {
       NER ner = new NER("","");
        List<List<Span>> spans = ner.getNer(new String[]{"Arthur", "was","an","old","goat"});
        assertEquals(spans.size(), 1);
        assertEquals(spans.get(0).get(0).getStart(), 0);
        assertEquals(spans.get(0).get(0).getEnd(), 1);
        assertEquals("person", spans.get(0).get(0).getType());
    }

}