package com.audit.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.audit.domain.CostCentreMaster;
import com.audit.domain.User;
import com.audit.repository.CostCentreRepository;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;

@Service
public class CostCentreService {

	private final CostCentreRepository costCentreRepository;
	private final UserRepository userRepository;
	
	private final ExampleMatcher search_matcher = ExampleMatcher.matching()
			.withMatcher("isActive", match -> match.exact())
			.withMatcher("costCentreName", match -> match.ignoreCase().contains());
	
	public CostCentreService(CostCentreRepository costCentreRepository, UserRepository userRepository) {
		this.costCentreRepository = costCentreRepository;
		this.userRepository = userRepository;
	}
	
	public List<CostCentreMaster> getAllCostCentre(){
		return costCentreRepository.findAllByIsActive(true);
	}
	
	public CostCentreMaster getCostCenterById(Long id){
		return costCentreRepository.findById(id).get();
	}
	
	public CostCentreMaster createCostCenter(CostCentreMaster costCenterMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		costCenterMaster.setCreatedBy(user.get());
		costCenterMaster.setLastModifiedBy(user.get());
		costCenterMaster.setCreatedDate(Instant.now());
		costCenterMaster.setLastModifiedDate(Instant.now());
		
		costCenterMaster.setOwnerId(userRepository.findById(1L).get());
		costCenterMaster.setReportingId(userRepository.findById(2L).get());
		
		return costCentreRepository.save(costCenterMaster);
	}
	
	public CostCentreMaster updateCostCenter(CostCentreMaster costCenterMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		
		CostCentreMaster update = costCentreRepository.findById(costCenterMaster.getId()).get();
		update.setCostCentreName(costCenterMaster.getCostCentreName());
		update.setIsActive(costCenterMaster.getIsActive());
		update.setLastModifiedBy(user.get());
		update.setLastModifiedDate(Instant.now());
		
		return costCentreRepository.save(update);
	}
	
	@SuppressWarnings("deprecation")
	public Map<String,Object> getAllCostCenter(CostCentreMaster costCenter,Integer page) {
		Map<String, Object> map = new HashMap<>();
		List<CostCentreMaster> locationMaster = costCentreRepository.findAll(Example.of(costCenter, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
		map.put("list", locationMaster);
		map.put("count", this.getCount(costCenter));
		return map;
    }
    
   @SuppressWarnings("deprecation")
   public List<CostCentreMaster> nextCostCenter(CostCentreMaster searchLocation,Integer page) {
		return costCentreRepository.findAll(Example.of(searchLocation, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
    }
    
    public Integer getCount(CostCentreMaster costCenter) {
    	Integer count = 0;
    	List<CostCentreMaster> all = costCentreRepository.findAll(Example.of(costCenter,search_matcher));
    	if(!all.isEmpty()) 
    		count =  all.size();
    	return count;
    }
}
