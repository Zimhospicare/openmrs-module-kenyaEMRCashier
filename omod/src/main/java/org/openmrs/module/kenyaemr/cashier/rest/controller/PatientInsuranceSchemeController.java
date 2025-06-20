package org.openmrs.module.kenyaemr.cashier.rest.controller;

import org.openmrs.Concept;
import org.openmrs.PersonAttribute;
import org.openmrs.api.ConceptService;
import org.openmrs.api.PersonService;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/rest/" + RestConstants.VERSION_1 + "/cashier")
public class PatientInsuranceSchemeController {
    @Autowired
    PersonService personService;

    @Autowired
    ConceptService conceptService;

    @RequestMapping(method = RequestMethod.GET, path = "/insurance-scheme")
    public HashMap<String, Object> getPatientInsuranceScheme(
            @RequestParam(value = "patientUuid", required = false) String patientUuid) {
        List<PersonAttribute> personAttributes = personService.getPersonByUuid(patientUuid).getActiveAttributes();
        HashMap<String, Object> personAttributeMap = new HashMap<>();
        personAttributeMap.put("hasInsurance", false);
        personAttributeMap.put("message", "Patient Not enrolled for to any Medical insurance scheme");
        if (personAttributes != null) {
            for (PersonAttribute personAttribute : personAttributes) {
                if (personAttribute.getAttributeType().getFormat().equals("org.openmrs.Concept")) {
                    Concept ConceptByValue = conceptService.getConcept(personAttribute.getValue());
                    if (personAttribute.getAttributeType().getUuid().equals("d2860828-00b8-475f-b8b9-3167719f5080")
                            && ConceptByValue.getUuid().equals("1065AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA")) {
                        personAttributeMap.put("hasInsurance", true);
                    }
                    if (personAttribute.getAttributeType().getUuid().equals("cf2f791b-cd29-4273-9f8d-4992ccbf28f7")) {
                        personAttributeMap.put("insuranceScheme", ConceptByValue.getName().getName());
                        personAttributeMap.put("message", "Patient enrolled for Medical insurance scheme with " + ConceptByValue.getName().getName());
                    }
                } else if (personAttribute.getAttributeType().getUuid().equals("9c1966f4-c696-425b-852c-b0ebaa9eb3be") && personAttribute.getAttributeType().getFormat().equals("java.lang.String")) {
                    personAttributeMap.put("policyNo", personAttribute.getValue());
                }
            }
            return personAttributeMap;
        }
        return personAttributeMap;
    }

}
