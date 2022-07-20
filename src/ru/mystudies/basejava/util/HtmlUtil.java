package ru.mystudies.basejava.util;

import ru.mystudies.basejava.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static ru.mystudies.basejava.model.SectionType.*;

public class HtmlUtil {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }

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
                return Arrays.stream(value.split("\n")).filter(s -> s.trim().length()!=0).collect(Collectors.toList());
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
        String start = DateUtil.format(period.getStart());
        if(start.equals("Сейчас")){
            return start;
        }

        if(start.equals("")){
            return "";
        }

        String end = period.getEnd() == null ? "Сейчас" : DateUtil.format(period.getEnd());
        return start + " / " + end;
    }

    public static String getPeriodStart(Period period) {
        return DateUtil.format(period.getStart()).equals("Сейчас") ? LocalDate.now().format(DateUtil.DATE_TIME_FORMATTER) : DateUtil.format(period.getStart());
    }

    public static String getPeriodEnd(Period period) {
        return DateUtil.format(period.getEnd());
    }
}
