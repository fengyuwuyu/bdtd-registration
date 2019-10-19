package com.bdtd.card.registration.modular.registration.service;

import java.util.List;
import java.util.Map;

import com.stylefeng.guns.scmmain.model.DtUser;

public interface IPatientRegistrationService {

	DtUser patientInfo(String userCard);
	
	Map<String, Object> patientInfoMap(String userCard);

    List<Map<String, Object>> findByUserName(String userName);

    List<Map<String, Object>> findDoctorByUserName(String userName);
    
    List<Map<String, Object>> findDtDepTree(Long parentId);

    List<DtUser> userList(String userLname, Integer orgId, String userDuty, String userWorkday, Integer offset,
            Integer limit);
    
    List<Map<String, Object>> findDistinctByUserDuty();

    List<Map<String, Object>> depSelect();
}
