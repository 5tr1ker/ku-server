package com.team.saver.search.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AutoComplete {

    @Id
    @GeneratedValue
    private long autoCompleteId;

    private String word;

    private int frequency;

    public static AutoComplete createAutoComplete(String word) {
        return AutoComplete.builder()
                .word(word)
                .frequency(0)
                .build();
    }

    public void increaseFrequency(int i) {
        this.frequency += i;
    }
}