package com.wevioo.pi.common;

import com.wevioo.pi.domain.enumeration.UserTypeEnum;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

public final class ApplicationConstants {

	public static final String IS_ADMIN_NULL = "IS_ADMIN_NULL";
	public static final String APPROVED_INTERMEDIARY_NULL = "APPROVED_INTERMEDIARY_NULL";
	public static final String TYPE_ADMINISTRATOR_IS_NULL = "APPROVED_INTERMEDIARY_NULL";

	public static final String FIRST_NAME_IS_REQUIRED = "FIRST_NAME_IS_REQUIRED";

	public static final String LAST_NAME_IS_REQUIRED = "LAST_NAME_IS_REQUIRED";
	public static final String UNIVERSITY_DEGREE_IS_REQUIRED = "UNIVERSITY_DEGREE_IS_REQUIRED";
	public static final String HOME_PHONE_NUMBER_IS_REQUIRED = "HOME_PHONE_NUMBER_IS_REQUIRED";

	public static final String CELL_PHONE_NUMBER_IS_REQUIRED = "HOME_PHONE_NUMBER_IS_REQUIRED";
	public static final String FAX_NUMBER_IS_REQUIRED = "FAX_NUMBER_IS_REQUIRED";
	public static final String BANK_APPROVED_INTERMEDIARY_IS_REQUIRED = "BANK_APPROVED_INTERMEDIARY_IS_REQUIRED";
	public static final String TYPE_ADMINISTRATOR_IS_REQUIRED = "TYPE_ADMINISTRATOR_IS_REQUIRED";
	public static final String DIRECTION_IS_REQUIRED = "DIRECTION_IS_REQUIRED";
	public static final String GRADE_IS_REQUIRED = "GRADE_IS_REQUIRED";
	public static final String FUNCTION_IS_REQUIRED = "FUNCTION_IS_REQUIRED";
	public static final String COUNTRY_NOT_FOUND = "COUNTRY_NOT_FOUND";

	public static final String ACTIVITY_SECTOR_NOT_FOUND = "ACTIVITY_SECTOR_NOT_FOUND";
	public static final String ACTIVITY_SUB_SECTOR_NOT_FOUND = "ACTIVITY_SUB_SECTOR_NOT_FOUND";
	public static final String ACTIVITY_GROUP_NOT_FOUND = "ACTIVITY_GROUP_NOT_FOUND";
	public static final String ACTIVITY_CLASS_NOT_FOUND = "ACTIVITY_CLASS_NOT_FOUND";
	public static final String ACTIVITY_SUPPORT_TYPE_NOT_FOUND = "ACTIVITY_SUPPORT_TYPE_NOT_FOUND";
	public static final String AUTHORITY_NOT_FOUND = "AUTHORITY_NOT_FOUND";

	public static final String LEGAL_FORM_NOT_FOUND = "LEGAL_FORM_NOT_FOUND";







	public static final String NO_COUNTRY_FOUNDED_WITH_ID = "No country founded with id: ";

	public static final String NO_ACTIVITY_SECTOR_FOUNDED_WITH_ID = "No activity sector founded with id: ";
	public static final String NO_ACTIVITY_SUB_SECTOR_FOUNDED_WITH_ID = "No activity sub sector founded with id: ";
	public static final String NO_ACTIVITY_GROUP_FOUNDED_WITH_ID = "No activity group founded with id: ";
	public static final String NO_ACTIVITY_CLASS_FOUNDED_WITH_ID = "No activity class founded with id: ";

	public static final String NO_ACTIVITY_SUPPORT_TYPE_FOUNDED_WITH_ID = "No activity support type founded with id: ";

	public static final String NO_AUTHORITY_FOUNDED_WITH_ID = "No authority founded with id: ";
	public static final String NO_LEGAL_FORM_FOUNDED_WITH_ID = "No legal form founded with id: ";








	public static final String NO_INVESTOR_FOUNDED_WITH_ID = "No Investor founded with id: ";


	/**
	 * Error codes
	 */

	public static final String BAD_REQUEST_CODE = "400";
	public static final String ERROR_INVALID_PASSWORD_FORMAT = "INVALID_PASSWORD_FORMAT";
	public static final String ERROR_LINK_NOT_FOUND = "LINK_NOT_FOUND";
	public static final String ERROR_MISMATCHED_PASSWORDS = "ERROR_MISMATCHED_PASSWORDS";
	public static final String ERROR_INVALID_SOCIAL_REASON_FORMAT = "INVALID_SOCIAL_REASON_FORMAT";

	public static final String KEY_GEN_NOT_FOUND_ERROR = "No KEYGEN found with type: ";
	public static final String KEY_GEN_NOT_FOUND_WITH_TYPE = "KEYGEN_NOT_FOUND_WITH_TYPE";

	public static final String FAILED_VALIDATION = "FAILED_VALIDATION";

	public static final String INVESTOR_NOT_FOUND_WITH_ID = "INVESTOR_NOT_FOUND_WITH_ID";

	public static final String ERROR_USER_NOT_FOUND = "ERROR_USER_NOT_FOUND";
	public static final String ERROR_USER_NOT_ALLOWED = "ERROR_USER_NOT_ALLOWED";
	public static final String ERROR_INTERNAL_SERVER_ERROR = "ERROR_INTERNAL_SERVER_ERROR";
	public static final String ERROR_UNAUTHORIZED_REQUEST = "ERROR_UNAUTHORIZED_REQUEST";
	public static final String ERROR_MISSING_REQUIRED_FILES = "ERROR_MISSING_REQUIRED_FILES";
	public static final String USER_NOT_FOUND = "USER_NOT_FOUND";

	/**
	 * Codes length
	 */
	public static final int SECURITY_CODE_LENGTH = 5;
	public static final int SECURITY_CODE_CITIZEN_LENGTH = 8;
	public static final int ACTIVATION_CODE_LENGTH = 20;
	public static final int AUTO_GENERATED_PASSWORD = 15;

	public static final String ERROR_EMAIL_ALREADY_EXISTS = "ERROR_EMAIL_ALREADY_EXISTS";

	public static final String PARTICIPATION_ALREADY_EXISTS = "PARTICIPATION_ALREADY_EXISTS";

	public static final String ERROR_MISSING_REQUIRED_DATA = "MISSING_REQUIRED_DATA";

	public static final String ERROR_MISSING_CONFIRMATION_PASSWORD = "ERROR_MISSING_CONFIRMATION_PASSWORD";

	public static final String ERROR_MAX_FIELD_LENGTH = "ERROR_MIN_MAX_FIELD_LENGTH";

	public static final String INVALID_LOCALE = "INVALID_LOCALE";

	public static final String CELL_PHONE = "cellPhone";
	public static final String SOCIAL_REASON = "socialReason";

	public static final String ERROR_INVALID_PHONE_FORMAT = "INVALID_PHONE_FORMAT";

	public static final String ERROR_INVALID_EMAIL_FORMAT = "INVALID_EMAIL_FORMAT";
	public static final String ERROR_UNEXPECTED_NULL_VALUE = "UNEXPECTED_NULL_VALUE";

	public static final int NUMBER_USER_OF_TYPE_BANKER = 2;
	public static final String ADDRESS = "address";

	public static final String SPACE = " ";

	public static final String PP_NON_RESIDENT_TUNISIAN_LAB = "Personne physique non-résidente de nationalité tunisienne";
	public static final String PP_NON_RESIDENT_FOREIGN_LAB = "Personne physique non-résidente de nationalité étrangère";
	public static final String PM_NON_RESIDENT_TUNISIAN_LAB = "Personne morale non-résidente de nationalité tunisienne";

	public static final String PM_NON_RESIDENT_FOREIGN_LAB = "Personne morale non-résidente de nationalité étrangère";

	public static final String ERROR_INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER = "INVALID_PAGE_OR_PAGE_CAPACITY_NUMBER";

	public static final String NUMBER_USER_OF_TYPE_ADMIN_BANKER_SUPERIOR = "NUMBER_USER_OF_TYPE_ADMIN_BANKER_SUPERIOR";

	public static final String CREATION_DATE = "creationDate";

	public static final String BANK_NOT_FOUND = "BANK_NOT_FOUND";
	public static final String SPECIFIC_REFERENTIAL_NOT_FOUND = "BANK_NOT_FOUND";
	public  static final  String DIRECT_INVEST_REQUEST_NOT_FOUND   = "DIRECT_INVEST_REQUEST_NOT_FOUND";

	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BCT_BANKER = Arrays.asList(UserTypeEnum.BANKER,
			UserTypeEnum.BCT_ADMIN);

	public static final List<UserTypeEnum> USER_TYPE_BCTS = Arrays.asList(UserTypeEnum.BCT_AGENT,
			UserTypeEnum.BCT_ADMIN);

	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BCT = List.of(UserTypeEnum.BCT_ADMIN);

	public static final List<UserTypeEnum> USER_TYPE_BCT_ADMIN_AND_ADMIN_BANKER = Arrays.asList(UserTypeEnum.BCT_ADMIN,
			UserTypeEnum.ADMIN_BANKER);

	public static final List<UserTypeEnum> USER_TYPE_ADMIN_BANKER = List.of(UserTypeEnum.ADMIN_BANKER);

	public static final List<UserTypeEnum> USER_TYPE_BANKER = List.of(UserTypeEnum.BANKER);


	public static final List<UserTypeEnum> USER_TYPE_BANKER_AND_INVESTOR= Arrays.asList(UserTypeEnum.BANKER,
			UserTypeEnum.INVESTOR);


	public  static  final  String SA = "SA";


	/**
	 * private constructor
	 */
	private ApplicationConstants() {

	}
}
