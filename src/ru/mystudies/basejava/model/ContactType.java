package ru.mystudies.basejava.model;

public enum ContactType {
    NUMBER_PHONE("Номер телефона"),
    SKYPE("Скайп"),
    EMAIL("Электронный адрес"),
    PROFILE_LINKEDIN("Профиль LinkedIn"),
    PROFILE_GITHUB("Профиль GitHub"),
    PROFILE_STACKOVERFLOW("Профиль Stackoverflow"),
    HOME_PAGE("Домашняя страница");
    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}

