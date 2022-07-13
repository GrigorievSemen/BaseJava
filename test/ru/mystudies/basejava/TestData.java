package ru.mystudies.basejava;
import ru.mystudies.basejava.model.*;

import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String FULL_NAME_1 = "Петров Петр Петрович";
    public static final String FULL_NAME_2 = "Иванов Иван Иванович";
    public static final String FULL_NAME_3 = "Сидоров Сергей Сергеевич";
    public static final String UUID_NOT_EXIST = "dummy";
    public static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    public static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    public static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);

    static {
        RESUME_1.addContact(ContactType.NUMBER_PHONE, "1111111111");
        RESUME_1.addContact(ContactType.SKYPE, "qwerty1");
        RESUME_1.addContact(ContactType.EMAIL, "mail1");
        RESUME_1.addContact(ContactType.PROFILE_LINKEDIN, "LinkedIn1");
        RESUME_1.addContact(ContactType.PROFILE_GITHUB, "GitHub1");
        RESUME_1.addContact(ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow1");
        RESUME_1.addContact(ContactType.HOME_PAGE, "Home1");

        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Позиция_1"));
        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Личные качества_1"));

        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection("Достижение_1", "Достижение_11"));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация_1", "Квалификация_11"));

//        RESUME_1.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Number_1", "Web_1",
//                                new Period(DateUtil.of(2001, Month.SEPTEMBER), DateUtil.of(2002, Month.SEPTEMBER), "description_1"))));
//
//        RESUME_1.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Number_1", "",
//                                new Period(DateUtil.of(1990, Month.MAY), DateUtil.of(1992, Month.MAY), "Education_1"),
//                                new Period(DateUtil.of(2005, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2"))));
//
        RESUME_2.addContact(ContactType.NUMBER_PHONE, "2222222222");
        RESUME_2.addContact(ContactType.SKYPE, "qwerty2");
        RESUME_2.addContact(ContactType.EMAIL, "mail2");
        RESUME_2.addContact(ContactType.PROFILE_LINKEDIN, "LinkedIn2");
        RESUME_2.addContact(ContactType.PROFILE_GITHUB, "GitHub2");
        RESUME_2.addContact(ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow2");
        RESUME_2.addContact(ContactType.HOME_PAGE, "Home2");

        RESUME_2.addSection(SectionType.OBJECTIVE, new TextSection("Objective_2"));
        RESUME_2.addSection(SectionType.PERSONAL, new TextSection("Personal_2"));

        RESUME_2.addSection(SectionType.ACHIEVEMENT, new ListSection("Achieve_2", "Achieve_22"));
        RESUME_2.addSection(SectionType.QUALIFICATIONS, new ListSection("Qualifications_2", "Qualifications_22"));

//        RESUME_2.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Number_2", "Web_2",
//                                new Period(DateUtil.of(2010, Month.SEPTEMBER), DateUtil.of(2012, Month.SEPTEMBER), "description_1"))));
//
//        RESUME_2.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Number_2", "",
//                                new Period(DateUtil.of(1991, Month.MAY), DateUtil.of(1995, Month.MAY), "Education_1"),
//                                new Period(DateUtil.of(2010, Month.FEBRUARY), DateUtil.of(2011, Month.FEBRUARY), "Education_2"))));
//
        RESUME_3.addContact(ContactType.NUMBER_PHONE, "3333333333");
        RESUME_3.addContact(ContactType.SKYPE, "qwerty3");
        RESUME_3.addContact(ContactType.EMAIL, "mail3");
        RESUME_3.addContact(ContactType.PROFILE_LINKEDIN, "LinkedIn3");
        RESUME_3.addContact(ContactType.PROFILE_GITHUB, "GitHub3");
        RESUME_3.addContact(ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow3");
        RESUME_3.addContact(ContactType.HOME_PAGE, "Home3");

        RESUME_3.addSection(SectionType.OBJECTIVE, new TextSection("Objective_3"));
        RESUME_3.addSection(SectionType.PERSONAL, new TextSection("Personal_3"));

        RESUME_3.addSection(SectionType.ACHIEVEMENT, new ListSection("Achieve_3", "Achieve_33"));
        RESUME_3.addSection(SectionType.QUALIFICATIONS, new ListSection("Qualifications_3", "Qualifications_33"));

//        RESUME_3.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Organization("Number_3", "Web_3",
//                                new Period(DateUtil.of(2015, Month.SEPTEMBER), DateUtil.of(2016, Month.SEPTEMBER), "description_1"))));
//
//        RESUME_3.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Organization("Number_3", "",
//                                new Period(DateUtil.of(1998, Month.MAY), DateUtil.of(1999, Month.MAY), "Education_1"),
//                                new Period(DateUtil.of(2007, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2"))));
    }
    }

