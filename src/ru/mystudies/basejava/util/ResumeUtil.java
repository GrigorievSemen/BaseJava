package ru.mystudies.basejava.util;

import ru.mystudies.basejava.model.*;

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
            if (value.length() != 0 && (OBJECTIVE.equals(sectionType) || PERSONAL.equals(sectionType))) {
                return List.of(value);
            } else if (value.length() != 0 && (ACHIEVEMENT.equals(sectionType) || QUALIFICATIONS.equals(sectionType))) {
                return List.of(value.split("\n"));
            }
        }
        return new ArrayList<>();
    }

    public static List<Organization> organizationsList(SectionType sectionType, Resume resume) {
        AbstractSection abstractSection = resume.getSection().get(sectionType);
        if (abstractSection != null) {
            return resume.getSection(sectionType).getItemsString();
        }
        return new ArrayList<>();
    }

    public static String getPeriod(Period period) {
        String start = period.getStart().toString();
        String end = period.getEnd() == null ? "сейчас" : period.getEnd().toString();
        return start + " / " + end;
    }

    public static String getPeriodStart(Period period) {
        String start = period.getStart().toString();
        return start;
    }

    public static String getPeriodEnd(Period period) {
        String end = period.getEnd() == null ? "" : period.getEnd().toString();
        return end;
    }
}
