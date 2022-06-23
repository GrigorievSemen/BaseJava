package ru.mystudies.basejava.storage.serializer;

import ru.mystudies.basejava.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class DataStreamSerializer implements StreamSerializer {

    @Override
    public void doWrite(Resume resume, OutputStream os) throws IOException {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());

            writeWithException(resume.getContacts().entrySet(), dos, entry -> {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            });

            Map<SectionType, AbstractSection> sections = resume.getSection();
            dos.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                AbstractSection section = entry.getValue();

                dos.writeUTF(sectionType.name());

                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        dos.writeUTF(((TextSection) section).getRecord());
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        writeWithException(((ListSection) section).getItems(), dos, dos::writeUTF);
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        writeWithException(((OrganizationSection) section).getOrganizations(), dos, organization -> {
                            dos.writeUTF(organization.getTitle());
                            dos.writeUTF(organization.getWebsite());
                            writeWithException(organization.getPeriods(), dos, period -> {
                                dos.writeUTF(period.getStart().toString());
                                dos.writeUTF(period.getEnd().toString());
                                dos.writeUTF(period.getDescription());
                            });
                        });
                        break;
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

            Operate(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));

            Operate(dis, () -> {
                SectionType sectionType = SectionType.valueOf(dis.readUTF());
                switch (sectionType) {
                    case OBJECTIVE:
                    case PERSONAL:
                        resume.addSection(sectionType, new TextSection(dis.readUTF()));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(sectionType, new ListSection(readWithException(dis, dis::readUTF)));
                        break;
                    case EXPERIENCE:
                    case EDUCATION:
                        resume.addSection(sectionType,
                                new OrganizationSection(readWithException(dis, () ->
                                        new Organization(dis.readUTF(), dis.readUTF(), readWithException(dis, () ->
                                                new Period(LocalDate.parse(dis.readUTF()), LocalDate.parse(dis.readUTF()), dis.readUTF()))))));
                        break;
                }
            });
            return resume;
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream dos, WriterValue<T> writerValue) throws IOException {
        dos.writeInt(collection.size());
        for (T value : collection) {
            writerValue.write(value);
        }
    }

    private void Operate(DataInputStream dis, Operate operate) throws IOException {
        int size = dis.readInt();
        for (int i = 0; i < size; i++) {
            operate.act();
        }
    }

    private <T> List readWithException(DataInputStream dis, ReadValue<T> readValue) throws IOException {
        int size = dis.readInt();
        List<T> collection = new ArrayList();
        for (int i = 0; i < size; i++) {
            collection.add(readValue.read());
        }
        return collection;
    }

    private interface WriterValue<T> {
        void write(T t) throws IOException;
    }

    private interface Operate {
        void act() throws IOException;
    }

    private interface ReadValue<T> {
        T read() throws IOException;
    }
}