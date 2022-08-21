package ru.mystudies.basejava.web;

import ru.mystudies.basejava.Config;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.Storage;
import ru.mystudies.basejava.util.DateUtil;

import java.time.Month;
import java.util.UUID;

public class MainSql {
    public static void main(String[] args) {
        Storage storage = Config.get().getStorage();
        Resume RESUME_1 = new Resume(UUID.randomUUID().toString(), "Иванов Пример Примерович");
        Resume RESUME_2 = new Resume(UUID.randomUUID().toString(), "Сидоров Пример Примерович");
        Resume RESUME_3 = new Resume(UUID.randomUUID().toString(), "Петров Пример Примерович");

        RESUME_1.setContact(ContactType.NUMBER_PHONE, "8(999)999-99-99");
        RESUME_1.setContact(ContactType.SKYPE, "myScype");
        RESUME_1.setContact(ContactType.EMAIL, "ivanov@mmail.ru");
      //  RESUME_1.setContact(ContactType.PROFILE_LINKEDIN, "https://yandex.ru");
        RESUME_1.setContact(ContactType.PROFILE_GITHUB, "https://github.com/.....");
        RESUME_1.setContact(ContactType.PROFILE_STACKOVERFLOW, "https://STACKOVERFLOW");
       // RESUME_1.setContact(ContactType.HOME_PAGE, "https://yandex.ru");

        RESUME_1.setSection(SectionType.OBJECTIVE, new TextSection("Не унывать!"));
        RESUME_1.setSection(SectionType.PERSONAL, new TextSection("Пример качество Пример качество Пример качество Пример качество "));

        RESUME_1.setSection(SectionType.ACHIEVEMENT, new ListSection("Пример достижения1", "Пример достижения2", "Пример достижения3", "Пример достижения4\n\n"));
        RESUME_1.setSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация_1", "Квалификация_2"));

        RESUME_1.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Второе место работы", "",
                                new Period(DateUtil.of(2005, Month.JANUARY), null, "Руководитель", "Описание рабочей деятельности")),
                        new Organization("Первое место работы", "",
                                new Period(DateUtil.of(2002, Month.MARCH), DateUtil.of(2004, Month.NOVEMBER), "Старший таксист", "Ездил сюда туда"),
                                new Period(DateUtil.of(2001, Month.FEBRUARY), DateUtil.of(2002, Month.MARCH), "Таксист", "Ездил туда сюда."))));

        RESUME_1.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Пример учебного заведения", "",
                                new Period(DateUtil.of(1995, Month.AUGUST), DateUtil.of(2000, Month.MAY), "Получил высшее образование",""),

                                new Period(DateUtil.of(1992, Month.SEPTEMBER), DateUtil.of(1995, Month.MAY), "Получил средние образование", ""))));
        RESUME_2.setContact(ContactType.NUMBER_PHONE, "8(999)999-99-99");
        RESUME_2.setContact(ContactType.SKYPE, "myScype");
        RESUME_2.setContact(ContactType.EMAIL, "sidorov@mmail.ru");
      //  RESUME_1.setContact(ContactType.PROFILE_LINKEDIN, "https://yandex.ru");
        RESUME_2.setContact(ContactType.PROFILE_GITHUB, "https://github.com/.....");
        RESUME_2.setContact(ContactType.PROFILE_STACKOVERFLOW, "https://STACKOVERFLOW");
       // RESUME_1.setContact(ContactType.HOME_PAGE, "https://yandex.ru");

        RESUME_2.setSection(SectionType.OBJECTIVE, new TextSection("Не унывать!"));
        RESUME_2.setSection(SectionType.PERSONAL, new TextSection("Пример качество Пример качество Пример качество Пример качество "));

        RESUME_2.setSection(SectionType.ACHIEVEMENT, new ListSection("Пример достижения1", "Пример достижения2", "Пример достижения3", "Пример достижения4\n\n"));
        RESUME_2.setSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация_1", "Квалификация_2"));

        RESUME_2.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Второе место работы", "",
                                new Period(DateUtil.of(2005, Month.JANUARY), null, "Руководитель", "Описание рабочей деятельности")),
                        new Organization("Первое место работы", "",
                                new Period(DateUtil.of(2002, Month.MARCH), DateUtil.of(2004, Month.NOVEMBER), "Старший таксист", "Ездил сюда туда"),
                                new Period(DateUtil.of(2001, Month.FEBRUARY), DateUtil.of(2002, Month.MARCH), "Таксист", "Ездил туда сюда."))));

        RESUME_2.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Пример учебного заведения", "",
                                new Period(DateUtil.of(1995, Month.AUGUST), DateUtil.of(2000, Month.MAY), "Получил высшее образование",""),

                                new Period(DateUtil.of(1992, Month.SEPTEMBER), DateUtil.of(1995, Month.MAY), "Получил средние образование", ""))));
        RESUME_3.setContact(ContactType.NUMBER_PHONE, "8(999)999-99-99");
        RESUME_3.setContact(ContactType.SKYPE, "myScype");
        RESUME_3.setContact(ContactType.EMAIL, "petrov@mmail.ru");
      //  RESUME_1.setContact(ContactType.PROFILE_LINKEDIN, "https://yandex.ru");
        RESUME_3.setContact(ContactType.PROFILE_GITHUB, "https://github.com/.....");
        RESUME_3.setContact(ContactType.PROFILE_STACKOVERFLOW, "https://STACKOVERFLOW");
       // RESUME_1.setContact(ContactType.HOME_PAGE, "https://yandex.ru");

        RESUME_3.setSection(SectionType.OBJECTIVE, new TextSection("Не унывать!"));
        RESUME_3.setSection(SectionType.PERSONAL, new TextSection("Пример качество Пример качество Пример качество Пример качество "));

        RESUME_3.setSection(SectionType.ACHIEVEMENT, new ListSection("Пример достижения1", "Пример достижения2", "Пример достижения3", "Пример достижения4\n\n"));
        RESUME_3.setSection(SectionType.QUALIFICATIONS, new ListSection("Квалификация_1", "Квалификация_2"));

        RESUME_3.setSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Второе место работы", "",
                                new Period(DateUtil.of(2005, Month.JANUARY), null, "Руководитель", "Описание рабочей деятельности")),
                        new Organization("Первое место работы", "",
                                new Period(DateUtil.of(2002, Month.MARCH), DateUtil.of(2004, Month.NOVEMBER), "Старший таксист", "Ездил сюда туда"),
                                new Period(DateUtil.of(2001, Month.FEBRUARY), DateUtil.of(2002, Month.MARCH), "Таксист", "Ездил туда сюда."))));

        RESUME_3.setSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Пример учебного заведения", "",
                                new Period(DateUtil.of(1995, Month.AUGUST), DateUtil.of(2000, Month.MAY), "Получил высшее образование",""),
                                new Period(DateUtil.of(1992, Month.SEPTEMBER), DateUtil.of(1995, Month.MAY), "Получил средние образование", ""))));

        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }
}
