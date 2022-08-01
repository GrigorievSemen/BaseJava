package ru.mystudies.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.*;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final Resume EMPTY = new Resume();

//    static {
//        EMPTY.setSection(SectionType.OBJECTIVE, TextSection.EMPTY);
//        EMPTY.setSection(SectionType.PERSONAL, TextSection.EMPTY);
//        EMPTY.setSection(SectionType.ACHIEVEMENT, ListSection.EMPTY);
//        EMPTY.setSection(SectionType.QUALIFICATIONS, ListSection.EMPTY);
//        EMPTY.setSection(SectionType.EXPERIENCE, new OrganizationSection(Organization.EMPTY));
//        EMPTY.setSection(SectionType.EDUCATION, new OrganizationSection(Organization.EMPTY));
//    }
    // Unique identifier
    private String uuid;
    private String fullName;


    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);

    public Resume() {
        this.fullName = ("noname");
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName.replaceAll("\\s+", " ");
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSection() {
        return sections;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public void setContact(ContactType type, String value) {
        contacts.put(type, value);
    }

    public void setSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contacts.equals(resume.contacts) &&
                sections.equals(resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }

    public void printContacts() {
        contacts.forEach((key, value) -> System.out.println(value));
    }

    public void printSection() {
        sections.forEach((key, value) -> System.out.println(key.getTitle() + value));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName).append("\n");
        contacts.forEach((key, value) -> sb.append(value).append("\n"));
        sb.append("\n");
        sections.forEach((key, value) -> sb.append(key.getTitle()).append("\n").append(value).append("\n"));
        return sb.toString();
    }
}