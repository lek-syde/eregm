package com.africapolicy.main;

import lombok.Data;

/**
 * @author Olalekan Folayan
 */
@Data
public class AvailabilityStatus {
    String string;

    public AvailabilityStatus(String string) {
        this.string = string;
    }
}
