package ru.mystudies.basejava.util;

import ru.mystudies.basejava.model.AbstractSection;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.model.SectionType;

import java.util.ArrayList;
import java.util.List;

public class ResumeUtil {

    public static List<String> sectionDataToString(AbstractSection abstractSection) {
        List<String> result = new ArrayList<>();
        String value = abstractSection.getItemsString();
        if (value != null && value.trim().length() != 0) {
            return List.of(value.split("\n"));
        }
        return result;
    }

    public static List<String> getDataBySectionType(SectionType sectionType, Resume resume) {
        AbstractSection abstractSection = resume.getSection().get(sectionType);
        return abstractSection != null ? List.of(abstractSection.getItemsString().split("\n")) : List.of("");
    }
}
