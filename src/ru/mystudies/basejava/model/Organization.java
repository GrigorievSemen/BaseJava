package ru.mystudies.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private static final long serialVersionUID = 1L;

    private String website;
    private String title;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String title, String website, Period... periods) {
        this.website = Objects.requireNonNull(website, "website must not be null");
        this.title = Objects.requireNonNull(title, "website must not be null");
        this.periods = List.of(periods);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return website.equals(that.website) && title.equals(that.title) && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(website, title, periods);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < periods.size(); i++) {
            sb.append(periods.get(i));
        }
        return website.equals("".trim()) ? title + "\n" + sb : title + "\n" + sb + website + "\n";
    }
}
