package com.sample.notetaking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotesDto {
    private int id;
    private String title;
    private String body;
}
