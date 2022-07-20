package ru.mystudies.basejava;

import ru.mystudies.basejava.model.*;

import java.util.Arrays;
import java.util.List;

public class ResumeTestData {

    public static Resume createAndReturnResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);
        return resume;
    }

    public static void addContact(Resume resume, ContactType type, String value) {
        resume.setContact(type, value);
    }

    public static void addObjectiveOrPersonal(Resume resume, SectionType type, String value) {
        resume.setSection(type, new TextSection(value));
    }

    public static void addAchieveOrQualifications(Resume resume, SectionType type, String... value) {
        List<String> values = Arrays.asList(value);
        resume.setSection(type, new ListSection(values));
    }

    public static void addExperienceOrEducation(Resume resume, SectionType type, Organization... organization) {
        resume.setSection(type, new OrganizationSection(List.of(organization)));
    }
}


