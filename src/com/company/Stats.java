package com.company;

public interface Stats {
    // A word is 5 characters and a space (wpm).
    static String getStats(double startTime, int errors, int charsTyped){
        String stats = "";
        double wpm;
        double acuracy;
        if (charsTyped == 0){
            acuracy = 0;
            wpm = 0;
        } else {
            acuracy = (((double) charsTyped - errors) / (double) charsTyped) * 100;
            double mins = (System.nanoTime() - startTime)/60000000000d;
            double words = ((double) charsTyped) / 6;
            wpm = words / mins;
        } if (charsTyped == 1){
            wpm = 0;
        }
        stats = stats + "WPM: " + String.format("%.3g", wpm);
        stats = stats + "  Acuracy: " + String.format("%.3g", acuracy) + "%";
        return stats;
    }
}
