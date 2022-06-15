package ru.mystudies.basejava.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Period implements Serializable {
    private static final long serialVersionUID = 1L;

    private final LocalDate start;
    private final LocalDate end;
    private final String description;

    public Period(LocalDate start, LocalDate end, String description) {
        this.description = description;
        this.start = start;
        this.end = end;
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