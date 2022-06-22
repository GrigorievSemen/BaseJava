package ru.mystudies.basejava.storage.serializer;

import ru.mystudies.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume r, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(r.getUuid());
            dos.writeUTF(r.getFullName());

            Map<ContactType, String> contacts = r.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }

            Map<SectionType, AbstractSection> section = r.getSection();
            int countTextSection = numberOfCoincidences(section.entrySet(), SectionType.OBJECTIVE, SectionType.PERSONAL);
            int countListSection = numberOfCoincidences(section.entrySet(), SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS);
            int countOrganizationSection = numberOfCoincidences(section.entrySet(), SectionType.EXPERIENCE, SectionType.EDUCATION);
            dos.writeInt(countTextSection);
            dos.writeInt(countListSection);
            dos.writeInt(countOrganizationSection);

            for (Map.Entry<SectionType, AbstractSection> entry : section.entrySet()) {

                if (isExist(entry.getKey(), SectionType.OBJECTIVE, SectionType.PERSONAL)) {
                    dos.writeUTF(entry.getKey().name());
                    TextSection textSection = (TextSection) entry.getValue();
                    dos.writeUTF(textSection.getRecord());
                }

                if (isExist(entry.getKey(), SectionType.ACHIEVEMENT, SectionType.QUALIFICATIONS)) {
                    dos.writeUTF(entry.getKey().name());
                    ListSection listSection = (ListSection) entry.getValue();
                    dos.writeInt(listSection.getItems().size());
                    for (String text : listSection.getItems()) {
                        dos.writeUTF(text);
                    }
                }

                if (isExist(entry.getKey(), SectionType.EXPERIENCE, SectionType.EDUCATION)) {
                    dos.writeUTF(entry.getKey().name());
                    OrganizationSection organizationSection = (OrganizationSection) entry.getValue();
                    List<Organization> organizations = organizationSection.getOrganizations();

                    dos.writeInt(organizations.size());
                    for (Organization organization : organizations) {
                        dos.writeUTF(organization.getWebsite());
                        dos.writeUTF(organization.getTitle());

                        List<Period> periods = organization.getPeriods();
                        dos.writeInt(periods.size());
                        for (Period period : periods) {
                            dos.writeUTF(period.getStart().toString());
                            dos.writeUTF(period.getEnd().toString());
                            dos.writeUTF(period.getDescription());
                        }
                    }
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();

            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }

            int countTextSection = dis.readInt();
            int countListSection = dis.readInt();
            int countOrganizationSection = dis.readInt();

            for (int i = 0; i < countTextSection; i++) {
                resume.addSection(SectionType.valueOf(dis.readUTF()), new TextSection(dis.readUTF()));
            }

            for (int i = 0; i < countListSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                List<String> items = new ArrayList<>();
                int count = dis.readInt();
                for (int j = 0; j < count; j++) {
                    items.add((dis.readUTF()));
                }

                resume.addSection(sectionType, new ListSection(items));
            }

            for (int i = 0; i < countOrganizationSection; i++) {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                List<Organization> organizations = new ArrayList<>();

                int count = dis.readInt();
                for (int j = 0; j < count; j++) {
                    String website = dis.readUTF();
                    String title = dis.readUTF();

                    List<Period> periods = new ArrayList<>();
                    int count1 = dis.readInt();
                    for (int k = 0; k < count1; k++) {
                        periods.add(new Period(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF()));
                    }
                    organizations.add(new Organization(title, website, periods));
                }
                resume.addSection(sectionType, new OrganizationSection(organizations));
            }

            return resume;
        }
    }

    private int numberOfCoincidences(Set<Map.Entry<SectionType, AbstractSection>> entry, SectionType... sectionType) {
        int count = 0;
        for (SectionType section : sectionType) {
            count += entry.stream().filter(x -> x.getKey().equals(section)).count();
        }
        return count;
    }

    private boolean isExist(SectionType... sectionType) {
        SectionType st[] = sectionType;
        if (st[0].equals(st[1]) || st[0].equals(st[2])) {
            return true;
        }
        return false;
    }
}