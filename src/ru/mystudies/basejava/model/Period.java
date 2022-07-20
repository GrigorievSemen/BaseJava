package ru.mystudies.basejava.model;

import ru.mystudies.basejava.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Period implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final Period EMPTY = new Period();
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate start;
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate end;
    private String description;
    private String position;

    public Period() {
    }

    public Period(LocalDate start, LocalDate end, String position, String description) {
        this.position = position == null ? "" : position;
        this.description = description == null ? "" : description;
        this.start = start;
        this.end = end;
    }

    public LocalDate getStart() {
        return start;
    }

    public LocalDate getEnd() {
        return end;
    }

    public String getDescription() {
        return description;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Period period = (Period) o;
        return start.equals(period.start) && end.equals(period.end) && description.equals(period.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, description);
    }

    @Override
    public String toString() {
        return start + " - " + end +
                "     " + description + "\n";
    }
}