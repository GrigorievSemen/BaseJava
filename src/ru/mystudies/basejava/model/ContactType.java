package ru.mystudies.basejava.model;

public enum ContactType {
    NUMBER_PHONE("����� ��������"),
    SKYPE("�����"),
    EMAIL("����������� �����"),
    PROFILE_LINKEDIN("������� LinkedIn"),
    PROFILE_GITHUB("������� GitHub"),
    PROFILE_STACKOVERFLOW("������� Stackoverflow"),
    HOME_PAGE("�������� ��������");
    private final String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}

