package org.openmrs.module.kenyaemr.cashier.api.util;

import org.openmrs.*;
import org.openmrs.api.context.Context;

public class ReceiptUtil {

    protected ReceiptUtil() {}

    public static String UNIQUE_PATIENT_NUMBER = "05a29f94-c0ed-11e2-94be-8c13b969e334";

    /**
     * Gets the PatientIdentifierType for a patient UPN
     *
     * @return
     */
    public static PatientIdentifierType getUniquePatientNumberIdentifierType() {
        return Context.getPatientService().getPatientIdentifierTypeByUuid(UNIQUE_PATIENT_NUMBER);
    }

}
