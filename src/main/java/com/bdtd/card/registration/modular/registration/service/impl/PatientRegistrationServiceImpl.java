package com.bdtd.card.registration.modular.registration.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bdtd.card.registration.modular.registration.service.IPatientRegistrationService;
import com.stylefeng.guns.core.util.StringUtil;
import com.stylefeng.guns.scmmain.dao.IDtUserDao;
import com.stylefeng.guns.scmmain.model.DtUser;
import com.stylefeng.guns.scmmain.service.IDtDepService;

@Service
public class PatientRegistrationServiceImpl implements IPatientRegistrationService {
	
	@Autowired
	private IDtUserDao dtUserDao;
	@Autowired
	private IDtDepService dtDepService;

	@Override
	public DtUser patientInfo(String userCard) {
		return dtUserDao.findByUserCard(userCard);
	}
	
	@Override
	public Map<String, Object> patientInfoMap(String userCard) {
		return dtUserDao.userToMap(dtUserDao.findByUserCardOrUserNo(userCard, userCard));
	}

    @Override
    public List<Map<String, Object>> findByUserName(String userName) {
        return dtUserDao.findByUserName(userName);
    }

    @Override
    public List<Map<String, Object>> findDoctorByUserName(String userName) {
        List<DtUser> dtUserList = null;
        if (StringUtil.isNullEmpty(userName)) {
            dtUserList = dtUserDao.findAllDoctor();
        } else {
            dtUserList = dtUserDao.findDoctorByUserName(userName);
        }
        return dtUserList.stream().map((item)->{
            return dtUserDao.userToMap(item);
        }).collect(Collectors.toList());
    }

    @Override
    public List<Map<String, Object>> findDtDepTree(Long parentId) {
        return dtDepService.tree(parentId);
    }

    @Override
    public  List<DtUser> userList(String userLname, Integer orgId, String userDuty, String userWorkday, Integer offset,
            Integer limit) {
        return dtUserDao.userList(userLname, orgId, userDuty, userWorkday, offset, limit);
    }

    @Override
    public List<Map<String, Object>> findDistinctByUserDuty() {
        return dtUserDao.findDistinctByUserDuty();
    }

    @Override
    public List<Map<String, Object>> depSelect() {
        return this.dtDepService.select();
    }

}
