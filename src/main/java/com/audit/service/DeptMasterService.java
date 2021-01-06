package com.audit.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.domain.DeptMaster;
import com.audit.domain.User;
import com.audit.repository.DeptMasterRepository;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;
import com.audit.service.dto.DeptMasterDto;

@Service
@Transactional
public class DeptMasterService {

	private final DeptMasterRepository deptMasterRepository;
	private final UserRepository userRepository;
	
	private final ExampleMatcher search_matcher = ExampleMatcher.matching()
			.withMatcher("isActive", match -> match.exact())
			.withMatcher("deptName", match -> match.ignoreCase().contains());
	
	public DeptMasterService(DeptMasterRepository deptMasterRepository,UserRepository userRepository) {
		this.deptMasterRepository= deptMasterRepository;
		this.userRepository = userRepository;
	}
	
	public List<DeptMasterDto> getAllDepartment(){
		return deptMasterRepository.findAllByIsActive(true).stream().map(DeptMasterDto::new).collect(Collectors.toList());
	}
	
	public DeptMaster getDeptById(Long id){
		return deptMasterRepository.findById(id).get();
	}
	
	public DeptMaster createDepartment(DeptMaster deptMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		deptMaster.setCreatedBy(user.get());
		deptMaster.setLastModifiedBy(user.get());
		deptMaster.setCreatedDate(Instant.now());
		deptMaster.setLastModifiedDate(Instant.now());
		return deptMasterRepository.save(deptMaster);
	}
	
	public DeptMaster updateDepartment(DeptMaster deptMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		
		DeptMaster update = deptMasterRepository.findById(deptMaster.getId()).get();
		update.setDeptName(deptMaster.getDeptName());
		update.setIsActive(deptMaster.getIsActive());
		update.setLastModifiedBy(user.get());
		update.setLastModifiedDate(Instant.now());
		
		return deptMasterRepository.save(update);
	}
	
	@SuppressWarnings("deprecation")
	public Map<String,Object> getAllDepartments(DeptMaster dept,Integer page) {
		Map<String, Object> map = new HashMap<>();
		List<DeptMaster> locationMaster = deptMasterRepository.findAll(Example.of(dept, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
		map.put("list", locationMaster);
		map.put("count", this.getCount(dept));
		return map;
    }
    
   @SuppressWarnings("deprecation")
   public List<DeptMaster> nextDepartments(DeptMaster searchDept,Integer page) {
		return deptMasterRepository.findAll(Example.of(searchDept, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
    }
    
    public Integer getCount(DeptMaster dept) {
    	Integer count = 0;
    	List<DeptMaster> all = deptMasterRepository.findAll(Example.of(dept,search_matcher));
    	if(!all.isEmpty()) 
    		count =  all.size();
    	return count;
    }
	
}
