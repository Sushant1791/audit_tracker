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

import com.audit.domain.CostCentreMaster;
import com.audit.security.AuthoritiesConstants;
import com.audit.service.CostCentreService;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class CostCentreResource {

	
	private final CostCentreService costCentreService;
	ObjectMapper mapper = new ObjectMapper();
	
	public CostCentreResource(CostCentreService costCentreService) {
		this.costCentreService = costCentreService;
	}
	
	@GetMapping("/getAllCostCentre")
    public List<CostCentreMaster> getAllDepartment() throws URISyntaxException {
		return costCentreService.getAllCostCentre();
    }
	
	@GetMapping("/costCentre/{id}")
    public CostCentreMaster getCostCentreById(@PathVariable("id") Long id) throws URISyntaxException {
		return costCentreService.getCostCenterById(id);
    }
	
	@PostMapping("/costCentre")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public CostCentreMaster createCostCentre(@Valid @RequestBody CostCentreMaster costCenter) throws URISyntaxException {
		return costCentreService.createCostCenter(costCenter);
    }
	
	@PutMapping("/costCentre")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public CostCentreMaster updateCostCenter(@Valid @RequestBody CostCentreMaster costCenter) throws URISyntaxException {
		return costCentreService.updateCostCenter(costCenter);
    }
	
	@PostMapping("/get/costCentre/all")
	public Map<String,Object> getAllCostCentre(@RequestParam("searchCostCentre") String _searchCostCentre,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchCostCentre : "+_searchCostCentre);
		CostCentreMaster search_costCenter 	= mapper.readValue(_searchCostCentre, CostCentreMaster.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		search_costCenter = this.setCostCentreModel(search_costCenter);
		return costCentreService.getAllCostCenter(search_costCenter, _page);
	}
    
    @PostMapping("/get/costCentre/next")
	public List<CostCentreMaster> nextCostCentre(@RequestParam("searchCostCentre") String _searchCostCentre,@RequestParam("page") String _page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchCostCentre : "+_searchCostCentre);
		CostCentreMaster search_location 	= mapper.readValue(_searchCostCentre, CostCentreMaster.class);
		Integer page	= mapper.readValue(_page, Integer.class);
		search_location = this.setCostCentreModel(search_location);
		return costCentreService.nextCostCenter(search_location, page);
	}
	
	public CostCentreMaster setCostCentreModel(CostCentreMaster costCenterMaster) {
		
		if(costCenterMaster.getCostCentreName()!=null && costCenterMaster.getCostCentreName().equalsIgnoreCase("")) {
			costCenterMaster.setCostCentreName(null);
		}
		
		costCenterMaster.setOwnerId(null);
		costCenterMaster.setReportingId(null);
		costCenterMaster.setIsActive(null);
    	costCenterMaster.setLastModifiedDate(null);
    	costCenterMaster.setCreatedDate(null);
    	costCenterMaster.setCreatedBy(null);
    	costCenterMaster.setLastModifiedBy(null);
    	costCenterMaster.setId(null);
    	
		return costCenterMaster;
	}
}
