package ru.mystudies.basejava.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class OrganizationSection extends AbstractSection {
    private static final long serialVersionUID = 1L;

    private List<Organization> organization = new ArrayList<>();

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> qualifications) {
        this.organization = qualifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organization.equals(that.organization);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organization);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < organization.size(); i++) {
            sb.append(i + 1 + ".").append(organization.get(i)).append("\n");
        }
        return sb.toString();
    }
}
