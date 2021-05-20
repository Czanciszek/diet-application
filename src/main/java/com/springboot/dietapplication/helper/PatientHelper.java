package com.springboot.dietapplication.helper;

import com.springboot.dietapplication.model.type.PatientType;
import org.joda.time.DateTime;

public class PatientHelper {

    public static int calculatePPM(PatientType patient, float weight, float activityLevel) {
        float ppmBase = (patient.isSex()) ? 655.1f : 66.5f;
        float weightFactor = (patient.isSex()) ? 9.563f : 13.75f;
        float heightFactor = (patient.isSex()) ? 1.85f : 5.003f;
        float ageFactor = (patient.isSex()) ? 4.676f : 6.775f;

        DateTime birthDateTime = new DateTime(patient.getBirthDate());
        DateTime currentTime = new DateTime();
        int age = currentTime.getYear() - birthDateTime.getYear();
        float height = patient.getBodyHeight();

        float ppm = ppmBase +
                weightFactor * weight +
                heightFactor * height +
                ageFactor * age;

        return (int) (ppm * activityLevel);
    }
}
