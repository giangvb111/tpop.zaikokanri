package com.tpop.zaikokanri.exceptions;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIErrorDetail {
    private Integer index ;
    private String field;
    private String errorCode;
    private String message;
}
