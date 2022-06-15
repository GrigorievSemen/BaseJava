package ru.mystudies.basejava.model;

public enum SectionType {
    OBJECTIVE("Позиция:\n"),
    PERSONAL("Личные качества:\n"),
    ACHIEVEMENT("Достижения:\n"),
    QUALIFICATIONS("Квалификация:\n"),
    EXPERIENCE("Опыт работы:\n"),
    EDUCATION("Образование:\n");

    private final String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
