package ru.mystudies.basejava;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.util.DateUtil;

import java.time.Month;
import java.util.UUID;

public class TestData {
    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();
    public static final String FULL_NAME_1 = "Петров Петр Петрович";
    public static final String FULL_NAME_2 = "Иванов Иван Иванович";
    public static final String FULL_NAME_3 = "Сидоров Сергей Сергеевич";
    public static final String FULL_NAME_4 = "Коля";
    public static final String UUID_NOT_EXIST = "dummy";
    public static final Resume RESUME_1 = new Resume(UUID_1, FULL_NAME_1);
    public static final Resume RESUME_2 = new Resume(UUID_2, FULL_NAME_2);
    public static final Resume RESUME_3 = new Resume(UUID_3, FULL_NAME_3);
    public static final Resume RESUME_4 = new Resume(UUID_4, FULL_NAME_4);


    static {
        RESUME_4.setContact(ContactType.NUMBER_PHONE, "44444");
        RESUME_4.setContact(ContactType.SKYPE, "Skype");

        RESUME_1.setContact(ContactType.NUMBER_PHONE, "1111111111");
        RESUME_1.setContact(ContactType.SKYPE, "https://yandex.ru");
        RESUME_1.setContact(ContactType.EMAIL, "https://yandex.ru");
        RESUME_1.setContact(ContactType.PROFILE_LINKEDIN, "https://yandex.ru");
        RESUME_1.setContact(ContactType.PROFILE_GITHUB, "https://yandex.ru");
        RESUME_1.setContact(ContactType.PROFILE_STACKOVERFLOW, "https://yandex.ru");
        RESUME_1.setContact(ContactType.HOME_PAGE, "https://yandex.ru");

        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Позиция_1"));
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Личные качества_1"));

        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Достижение_1", "Достижение_11"));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация_1", "Квалификация_11"));

        RESUME_1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Name organization_1", "Web_1",
                                new Period(DateUtil.of(2001, Month.SEPTEMBER), DateUtil.of(2002, Month.DECEMBER), "Опыт_1", "Заголовок описания_1")),
                        new Organization("Name organization_2", "Web_2",
                                new Period(DateUtil.of(2002, Month.JANUARY), DateUtil.of(2003, Month.MAY), "Опыт_2", "Заголовок описания_2"),
                                new Period(DateUtil.of(2003, Month.FEBRUARY), DateUtil.of(2004, Month.MARCH), "Опыт_3", "Заголовок описания_3"))));

        RESUME_1.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Name organization_3", "",
                                new Period(DateUtil.of(1998, Month.MAY), DateUtil.of(1999, Month.MAY), "Education_1",""),
                                new Period(DateUtil.of(2007, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2", ""))));

        RESUME_2.setContact(ContactType.NUMBER_PHONE, "2222222222");
        RESUME_2.setContact(ContactType.SKYPE, "qwerty2");
        RESUME_2.setContact(ContactType.EMAIL, "mail2");
        RESUME_2.setContact(ContactType.PROFILE_LINKEDIN, "LinkedIn2");
        RESUME_2.setContact(ContactType.PROFILE_GITHUB, "GitHub2");
        RESUME_2.setContact(ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow2");
        RESUME_2.setContact(ContactType.HOME_PAGE, "Home2");

        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("Objective_2"));
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("Personal_2"));

        RESUME_2.setSection(SectionType.ACHIEVEMENT, new ListSection("Achieve_2", "Achieve_22"));
        RESUME_2.setSection(SectionType.QUALIFICATIONS, new ListSection("Qualifications_2", "Qualifications_22"));

        RESUME_2.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Name organization_1", "Web_1",
                                new Period(DateUtil.of(2001, Month.SEPTEMBER), DateUtil.of(2002, Month.DECEMBER), "Опыт_1", "Заголовок описания_1")),
                        new Organization("Name organization_2", "Web_2",
                                new Period(DateUtil.of(2002, Month.JANUARY), DateUtil.of(2003, Month.MAY), "Опыт_2", "Заголовок описания_2"),
                                new Period(DateUtil.of(2003, Month.FEBRUARY), DateUtil.of(2004, Month.MARCH), "Опыт_3", "Заголовок описания_3"))));

        RESUME_2.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Name organization_3", "",
                                new Period(DateUtil.of(1998, Month.MAY), DateUtil.of(1999, Month.MAY), "Education_1",""),
                                new Period(DateUtil.of(2007, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2", ""))));

        RESUME_3.setContact(ContactType.NUMBER_PHONE, "3333333333");
        RESUME_3.setContact(ContactType.SKYPE, "qwerty3");
        RESUME_3.setContact(ContactType.EMAIL, "mail3");
        RESUME_3.setContact(ContactType.PROFILE_LINKEDIN, "LinkedIn3");
        RESUME_3.setContact(ContactType.PROFILE_GITHUB, "GitHub3");
        RESUME_3.setContact(ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow3");
        RESUME_3.setContact(ContactType.HOME_PAGE, "Home3");

        RESUME_3.setSection(SectionType.OBJECTIVE, new TextSection("Objective_3"));
        RESUME_3.setSection(SectionType.PERSONAL, new TextSection("Personal_3"));

        RESUME_3.setSection(SectionType.ACHIEVEMENT, new ListSection("Achieve_3", "Achieve_33"));
        RESUME_3.setSection(SectionType.QUALIFICATIONS, new ListSection("Qualifications_3", "Qualifications_33"));

        RESUME_3.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Name organization_1", "Web_1",
                                new Period(DateUtil.of(2001, Month.SEPTEMBER), DateUtil.of(2002, Month.DECEMBER), "Опыт_1", "Заголовок описания_1")),
                        new Organization("Name organization_2", "Web_2",
                                new Period(DateUtil.of(2002, Month.JANUARY), DateUtil.of(2003, Month.MAY), "Опыт_2", "Заголовок описания_2"),
                                new Period(DateUtil.of(2003, Month.FEBRUARY), DateUtil.of(2004, Month.MARCH), "Опыт_3", "Заголовок описания_3"))));

        RESUME_3.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Name organization_3", "",
                                new Period(DateUtil.of(1998, Month.MAY), DateUtil.of(1999, Month.MAY), "Education_1",""),
                                new Period(DateUtil.of(2007, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2", ""))));
    }
    }

