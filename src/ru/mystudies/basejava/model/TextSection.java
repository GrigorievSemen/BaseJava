package ru.mystudies.basejava.model;

import java.util.Objects;

public class TextSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private String record;

    public TextSection() {
    }

    public TextSection(String record) {
        this.record = record;
    }

    @Override
    public String getItemsString() {
        return record;
    }

    public String getRecord() {
        return record;
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
        return record + "\n";
    }
}
