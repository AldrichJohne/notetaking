package com.sample.notetaking.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseObject {
    private response response;
    private ErrorResponseObj error;
    private String status;
}
