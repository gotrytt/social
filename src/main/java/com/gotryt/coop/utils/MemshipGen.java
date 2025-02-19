package com.gotryt.coop.utils;

import java.time.Year;

public class MemshipGen {

    public static String genMemship(int ledgerNumber) {

        int currentYear = Year.now().getValue();

        // Pad the user ID to 4 digits (e.g., 1 becomes "0001")
        String paddedId = String.format("%04d", ledgerNumber);

        // Combine year and padded user ID
        return currentYear + paddedId;
    }
}
