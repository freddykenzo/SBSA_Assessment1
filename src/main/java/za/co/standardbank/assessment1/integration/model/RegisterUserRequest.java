package za.co.standardbank.assessment1.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterUserRequest {

    private String leadId;
    private String leadDefinitionId;
    private String versionId;
    private String quoteNumber;
    private String salesConsultantNo;
    private String salesPersonName;
    private String salesPersonSurname;
    private String salesPersonEmail;
    private String comments;
    private String communicationTypeId;

}
