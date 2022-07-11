package ru.mystudies.basejava.util;


import org.junit.jupiter.api.Test;
import ru.mystudies.basejava.model.AbstractSection;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.model.TextSection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.mystudies.basejava.TestData.RESUME_1;

public class JsonParserTest {

    @Test
    public void testResume() throws Exception {
        String json = JsonParser.write(RESUME_1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void write() throws Exception {
        AbstractSection section1 = new TextSection("Objective1");
        String json = JsonParser.write(section1, AbstractSection.class);
        System.out.println(json);
        AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
        assertEquals(section1, section2);
    }
}