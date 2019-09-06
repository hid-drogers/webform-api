package com.hdisolutions.oltpservices.model.dto.palogicservices;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hdisolutions.model.dto.BaseInvokerResponse;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MemberSearchResponse extends BaseInvokerResponse {
    
	@JsonProperty("Result")
	private MemberSearchResponseResult result;
}

//SAMPLE RESPONSE
//{
//    "Result": {
//        "List": [{"MemberNumber": "115869899",
//					"MemberCardId": "94710524262",
//				    "FirstName": "Jane",
//					"LastName": "Brown",
//					"Dob": "1971-10-27",
//					"PersonCode": "01",
//					"OrganizationName": "",
//					"CarrierName": "",
//					"AccountName": "",
//					"GroupName": "",
//					"OrganizationId": "72727",
//					"CarrierId": "836737",
//					"AccountId": "7637727",
//					"GroupId": "728241"}],
//        "PageNumber": 1,
//        "PageSize": 5,
//        "TotalItems": 0,
//        "TotalPages": 0
//    },
//    "ErrorOccurred": false,
//    "ErrorMessage": null,
//    "ErrorSeverity": "None",
//    "ResponseText": null,
//    "StatusCode": 0
//}