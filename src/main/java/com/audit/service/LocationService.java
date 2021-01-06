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

import com.audit.domain.LocationMaster;
import com.audit.domain.User;
import com.audit.repository.LocationRepository;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;

@Service
public class LocationService {

	private final LocationRepository locationRepository;
	private final UserRepository userRepository;
	
	private final ExampleMatcher search_matcher = ExampleMatcher.matching()
			.withMatcher("isActive", match -> match.exact())
			.withMatcher("locationName", match -> match.ignoreCase().contains())
			.withMatcher("locationAddress", match -> match.ignoreCase().contains())
			.withMatcher("erpCode", match -> match.ignoreCase().contains());
	
	public LocationService(LocationRepository locationRepository, UserRepository userRepository) {
		this.locationRepository = locationRepository;
		this.userRepository = userRepository;
	}
	
	public List<LocationMaster> getAllActiveLocations(){
		return locationRepository.findAllByIsActive(true);
	}
	
	public LocationMaster getLocationById(Long id){
		return locationRepository.findById(id).get();
	}
	
	public LocationMaster createLocation(LocationMaster locationMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		locationMaster.setCreatedBy(user.get());
		locationMaster.setLastModifiedBy(user.get());
		locationMaster.setCreatedDate(Instant.now());
		locationMaster.setLastModifiedDate(Instant.now());
		return locationRepository.save(locationMaster);
	}
	
	public LocationMaster updateLocation(LocationMaster locationMaster){
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()): Optional.empty();
		
		LocationMaster update = locationRepository.findById(locationMaster.getId()).get();
		update.setLocationName(locationMaster.getLocationName());
		update.setLocationAddress(locationMaster.getLocationAddress());
		update.setIsActive(locationMaster.getIsActive());
		update.setLastModifiedBy(user.get());
		update.setLastModifiedDate(Instant.now());
		
		return locationRepository.save(update);
	}
	
	
	@SuppressWarnings("deprecation")
	public Map<String,Object> getAllLocations(LocationMaster location,Integer page) {
		Map<String, Object> map = new HashMap<>();
		List<LocationMaster> locationMaster = locationRepository.findAll(Example.of(location, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
		map.put("list", locationMaster);
		map.put("count", this.getCount(location));
		return map;
    }
    
   @SuppressWarnings("deprecation")
   public List<LocationMaster> nextLocations(LocationMaster searchLocation,Integer page) {
		return locationRepository.findAll(Example.of(searchLocation, search_matcher),
				new PageRequest(page, 10, Sort.Direction.DESC, "createdDate")).getContent();
    }
    
    public Integer getCount(LocationMaster location) {
    	Integer count = 0;
    	List<LocationMaster> all = locationRepository.findAll(Example.of(location,search_matcher));
    	if(!all.isEmpty()) 
    		count =  all.size();
    	return count;
    }
}
