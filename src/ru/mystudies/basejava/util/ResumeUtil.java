package ru.mystudies.basejava.util;

import ru.mystudies.basejava.model.AbstractSection;
import ru.mystudies.basejava.model.Resume;
import ru.mystudies.basejava.model.SectionType;

import java.util.ArrayList;
import java.util.List;

import static ru.mystudies.basejava.model.SectionType.*;

public class ResumeUtil {

    public static String sectionDataToString(SectionType sectionType, Resume resume) {
        AbstractSection abstractSection = resume.getSection().get(sectionType);
        String value = "";
        if (abstractSection != null) {
            value = abstractSection.getItemsString();
            if (value != null && value.trim().length() != 0) {
                return value;
            }
        }
        return value;
    }

    public static List<String> sectionDataToList(SectionType sectionType, Resume resume) {
        AbstractSection abstractSection = resume.getSection().get(sectionType);

        if (abstractSection != null) {
            String value = abstractSection.getItemsString();
            if (value != null && value.trim().length() != 0) {
                if (OBJECTIVE.equals(sectionType) || PERSONAL.equals(sectionType)) {
                    return List.of(value);
                } else if (ACHIEVEMENT.equals(sectionType) || QUALIFICATIONS.equals(sectionType)) {
                    return List.of(value.split("\n"));
                }
            }
        }
        return new ArrayList<>();
    }
}
