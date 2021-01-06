package com.audit.web.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.audit.domain.LocationMaster;
import com.audit.security.AuthoritiesConstants;
import com.audit.service.LocationService;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class LocationResource {

	private final LocationService locationService;
	ObjectMapper mapper = new ObjectMapper();

	public LocationResource(LocationService locationService) {
		this.locationService = locationService;
	}

	@GetMapping("/location")
	public List<LocationMaster> getAll() {
		return locationService.getAllActiveLocations();
	}
	
	@GetMapping("/location/{id}")
    public LocationMaster getLocatioById(@PathVariable("id") Long id) throws URISyntaxException {
		return locationService.getLocationById(id);
    }
	
	@PostMapping("/location")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public LocationMaster createLocation(@Valid @RequestBody LocationMaster location) throws URISyntaxException {
		return locationService.createLocation(location); 
    }
	
	@PutMapping("/location")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public LocationMaster updateLocation(@Valid @RequestBody LocationMaster location) throws URISyntaxException {
		return locationService.updateLocation(location); 
    }
	
	@PostMapping("/get/locations/all")
	public Map<String,Object> getAlllocations(@RequestParam("searchLocation") String _searchLocation,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchLocation : "+_searchLocation);
		LocationMaster search_location 	= mapper.readValue(_searchLocation, LocationMaster.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		search_location = this.setLocationModel(search_location);
		System.out.println("AFter : "+_searchLocation);
		return locationService.getAllLocations(search_location, _page);
	}
    
    @PostMapping("/get/locations/next")
	public List<LocationMaster> nextJobs(@RequestParam("searchLocation") String _searchLocation,@RequestParam("page") String _page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchLocation : "+_searchLocation);
		LocationMaster search_location 	= mapper.readValue(_searchLocation, LocationMaster.class);
		Integer page	= mapper.readValue(_page, Integer.class);
		search_location = this.setLocationModel(search_location);
		return locationService.nextLocations(search_location, page);
	}
    

    public LocationMaster setLocationModel(LocationMaster locationMaster) {
	
		if(locationMaster.getLocationName()!=null && locationMaster.getLocationName().equalsIgnoreCase("")) {
			locationMaster.setLocationName(null);
		}
		if(locationMaster.getLocationAddress()!=null && locationMaster.getLocationAddress().equalsIgnoreCase("")) {
			locationMaster.setLocationAddress(null);
		}
		if(locationMaster.getErpCode()!=null && locationMaster.getErpCode().equalsIgnoreCase("")) {
	    		locationMaster.setErpCode(null);
	    }
		
		locationMaster.setIsActive(null);
    	locationMaster.setLastModifiedDate(null);
    	locationMaster.setCreatedDate(null);
    	locationMaster.setCreatedBy(null);
    	locationMaster.setLastModifiedBy(null);
    	locationMaster.setId(null);
    	
		return locationMaster;
	}
}
