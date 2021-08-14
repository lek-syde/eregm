package com.africapolicy.main.response;

import lombok.Data;

/**
 * @author Olalekan Folayan
 */
@Data
public class StatusMessage {
    String status;
    String message;



    public StatusMessage(String status, String message) {
        this.status = status;
        this.message = message;
    }
}
