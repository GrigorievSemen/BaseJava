package ru.mystudies.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.Objects;
public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String record;

    public TextSection() {
    }

    public TextSection(String record) {
        this.record = record + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TextSection that = (TextSection) o;
        return record.equals(that.record);
    }

    @Override
    public int hashCode() {
        return Objects.hash(record);
    }

    public String toString() {
        return record;
    }
}
