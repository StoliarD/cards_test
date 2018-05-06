package ru.cards_mobile.test.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import net.jcip.annotations.Immutable;

import java.util.Date;
import java.util.Objects;

@Immutable
public class Account {

    private String snils;
    private String fio;
    private Date birthDate;

    public Account(String snils, String fio, Date birthDate) {
        this.snils = Objects.requireNonNull(snils);
        this.fio = Objects.requireNonNull(fio);
        this.birthDate = Objects.requireNonNull(birthDate);
    }

    public String getSnils() {
        return snils;
    }

    public String getFio() {
        return fio;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public Date getBirthDate() {
        return (Date) birthDate.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(snils, account.snils) &&
                Objects.equals(fio, account.fio) &&
                Objects.equals(birthDate, account.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(snils, fio, birthDate);
    }
}
