import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.mystudies.basejava.ResumeTestData;
import ru.mystudies.basejava.exception.ExistStorageException;
import ru.mystudies.basejava.exception.NotExistStorageException;
import ru.mystudies.basejava.model.*;
import ru.mystudies.basejava.storage.Storage;
import ru.mystudies.basejava.util.DateUtil;

import java.io.File;
import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("C:\\Users\\Sem\\Desktop\\Programming\\JavaProject\\IdeaProjects\\BaseJava\\HW\\storage");
    private static final Comparator<Resume> RESUME_COMPARATOR =
            Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String FULL_NAME_1 = "Петров Петр Петрович";
    private static final String FULL_NAME_2 = "Иванов Иван Иванович";
    private static final String FULL_NAME_3 = "Сидоров Сергей Сергеевич";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = ResumeTestData.createAndReturnResume(UUID_1, FULL_NAME_1);
    private static final Resume RESUME_2 = ResumeTestData.createAndReturnResume(UUID_2, FULL_NAME_2);
    private static final Resume RESUME_3 = ResumeTestData.createAndReturnResume(UUID_3, FULL_NAME_3);

    static {
        ResumeTestData.addContact(RESUME_1, ContactType.NUMBER_PHONE, "1111111111");
        ResumeTestData.addContact(RESUME_1, ContactType.SKYPE, "qwerty1");
        ResumeTestData.addContact(RESUME_1, ContactType.EMAIL, "mail1");
        ResumeTestData.addContact(RESUME_1, ContactType.PROFILE_LINKEDIN, "LinkedIn1");
        ResumeTestData.addContact(RESUME_1, ContactType.PROFILE_GITHUB, "GitHub1");
        ResumeTestData.addContact(RESUME_1, ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow1");
        ResumeTestData.addContact(RESUME_1, ContactType.HOME_PAGE, "Home1");

        ResumeTestData.addObjectiveOrPersonal(RESUME_1, SectionType.OBJECTIVE, "Good1");
        ResumeTestData.addObjectiveOrPersonal(RESUME_1, SectionType.PERSONAL, "Good boy1");

        ResumeTestData.addAchieveOrQualifications(RESUME_1, SectionType.ACHIEVEMENT, "Achieve_1", "Achieve_1");
        ResumeTestData.addAchieveOrQualifications(RESUME_1, SectionType.QUALIFICATIONS, "Qualifications_1", "Qualifications_1");

        ResumeTestData.addExperienceOrEducation(RESUME_1, SectionType.EXPERIENCE,
                new Organization("Number_1", "Web_1",
                        new Period(DateUtil.of(2001, Month.SEPTEMBER), DateUtil.of(2002, Month.SEPTEMBER), "description_1")));

        ResumeTestData.addExperienceOrEducation(RESUME_1, SectionType.EDUCATION,
                new Organization("Number_1", "",
                        new Period(DateUtil.of(1990, Month.MAY), DateUtil.of(1992, Month.MAY), "Education_1"),
                        new Period(DateUtil.of(2005, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2")));

        ResumeTestData.addContact(RESUME_2, ContactType.NUMBER_PHONE, "2222222222");
        ResumeTestData.addContact(RESUME_2, ContactType.SKYPE, "qwerty2");
        ResumeTestData.addContact(RESUME_2, ContactType.EMAIL, "mail2");
        ResumeTestData.addContact(RESUME_2, ContactType.PROFILE_LINKEDIN, "LinkedIn2");
        ResumeTestData.addContact(RESUME_2, ContactType.PROFILE_GITHUB, "GitHub2");
        ResumeTestData.addContact(RESUME_2, ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow2");
        ResumeTestData.addContact(RESUME_2, ContactType.HOME_PAGE, "Home2");

        ResumeTestData.addObjectiveOrPersonal(RESUME_2, SectionType.OBJECTIVE, "Good2");
        ResumeTestData.addObjectiveOrPersonal(RESUME_2, SectionType.PERSONAL, "Good boy2");

        ResumeTestData.addAchieveOrQualifications(RESUME_2, SectionType.ACHIEVEMENT, "Achieve_2", "Achieve_2");
        ResumeTestData.addAchieveOrQualifications(RESUME_2, SectionType.QUALIFICATIONS, "Qualifications_2", "Qualifications_2");

        ResumeTestData.addExperienceOrEducation(RESUME_2, SectionType.EXPERIENCE,
                new Organization("Number_2", "Web_2",
                        new Period(DateUtil.of(2010, Month.SEPTEMBER), DateUtil.of(2012, Month.SEPTEMBER), "description_1")));

        ResumeTestData.addExperienceOrEducation(RESUME_2, SectionType.EDUCATION,
                new Organization("Number_2", "",
                        new Period(DateUtil.of(1991, Month.MAY), DateUtil.of(1995, Month.MAY), "Education_1"),
                        new Period(DateUtil.of(2010, Month.FEBRUARY), DateUtil.of(2011, Month.FEBRUARY), "Education_2")));

        ResumeTestData.addContact(RESUME_3, ContactType.NUMBER_PHONE, "3333333333");
        ResumeTestData.addContact(RESUME_3, ContactType.SKYPE, "qwerty3");
        ResumeTestData.addContact(RESUME_3, ContactType.EMAIL, "mail3");
        ResumeTestData.addContact(RESUME_3, ContactType.PROFILE_LINKEDIN, "LinkedIn3");
        ResumeTestData.addContact(RESUME_3, ContactType.PROFILE_GITHUB, "GitHub3");
        ResumeTestData.addContact(RESUME_3, ContactType.PROFILE_STACKOVERFLOW, "Stackoverflow3");
        ResumeTestData.addContact(RESUME_3, ContactType.HOME_PAGE, "Home3");

        ResumeTestData.addObjectiveOrPersonal(RESUME_3, SectionType.OBJECTIVE, "Good3");
        ResumeTestData.addObjectiveOrPersonal(RESUME_3, SectionType.PERSONAL, "Good boy3");

        ResumeTestData.addAchieveOrQualifications(RESUME_3, SectionType.ACHIEVEMENT, "Achieve_3", "Achieve_3");
        ResumeTestData.addAchieveOrQualifications(RESUME_3, SectionType.QUALIFICATIONS, "Qualifications_3", "Qualifications_3");

        ResumeTestData.addExperienceOrEducation(RESUME_3, SectionType.EXPERIENCE,
                new Organization("Number_3", "Web_3",
                        new Period(DateUtil.of(2015, Month.SEPTEMBER), DateUtil.of(2016, Month.SEPTEMBER), "description_1")));

        ResumeTestData.addExperienceOrEducation(RESUME_3, SectionType.EDUCATION,
                new Organization("Number_3", "",
                        new Period(DateUtil.of(1998, Month.MAY), DateUtil.of(1999, Month.MAY), "Education_1"),
                        new Period(DateUtil.of(2007, Month.FEBRUARY), DateUtil.of(2008, Month.FEBRUARY), "Education_2")));
    }

    final private Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_3, FULL_NAME_3);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_3));
    }

    @Test
    public void updateNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(new Resume());
        });
    }

    @Test
    public void getAllSorted() throws IOException {
        List<Resume> resumes = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2, RESUME_3));
        sortedArray(resumes);
        Assertions.assertArrayEquals(resumes.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    public void get() {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.get(UUID_NOT_EXIST);
        });
    }

    @Test
    public void save() {
        Resume RESUME_4 = new Resume();
        storage.save(RESUME_4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() {
        ExistStorageException thrown = Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_1);
        });
    }

    @Test
    public void delete() throws IOException {
        List<Resume> resumes = new ArrayList<>(Arrays.asList(RESUME_1, RESUME_2));
        sortedArray(resumes);

        storage.delete(UUID_3);
        Assertions.assertArrayEquals(resumes.toArray(), storage.getAllSorted().toArray());
    }

    @Test
    public void deleteNotExist() {
        NotExistStorageException thrown = Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }

    public void assertSize(int expectedAmount) {
        Assertions.assertEquals(expectedAmount, storage.size());
    }

    public void assertGet(Resume resume) {
      assertEquals(resume, storage.get(resume.getUuid()));
    }

    public List<Resume> sortedArray(List<Resume> resumes) {
        List<Resume> list = resumes;
        list.sort(RESUME_COMPARATOR);
        return list;
    }
}