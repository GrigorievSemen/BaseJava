package ru.mystudies.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Serializable {
    private static final long serialVersionUID = 1L;
    // Unique identifier
    private final String uuid;
    private final String fullName;


    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);
    private final Map<SectionType, AbstractSection> section = new EnumMap<>(SectionType.class);

    public Resume() {
        this.uuid = UUID.randomUUID().toString();
        this.fullName = ("noname");
    }

    public Resume(String uuid, String fullName) {
        this.uuid = Objects.requireNonNull(uuid, "uuid must not be null");
        this.fullName = Objects.requireNonNull(fullName, "fullName must not be null");
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSection() {
        return section;
    }

    public void addContact(ContactType type, String field) {
        contacts.put(type, field);
    }

    public void addSection(SectionType type, AbstractSection abstractSection) {
        section.put(type, abstractSection);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) &&
                fullName.equals(resume.fullName) &&
                contacts.equals(resume.contacts) &&
                section.equals(resume.section);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, section);
    }

    public void printContacts() {
        contacts.forEach((key, value) -> System.out.println(value));
    }

    public void printSection() {
        section.forEach((key, value) -> System.out.println(key.getTitle() + value));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(fullName).append("\n");
        contacts.forEach((key, value) -> sb.append(value).append("\n"));
        sb.append("\n");
        section.forEach((key, value) -> sb.append(key.getTitle()).append("\n").append(value).append("\n"));
        return sb.toString();
    }
}