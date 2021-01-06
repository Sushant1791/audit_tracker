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

import com.audit.domain.DeptMaster;
import com.audit.security.AuthoritiesConstants;
import com.audit.service.DeptMasterService;
import com.audit.service.dto.DeptMasterDto;
import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api")
public class DeptMasterResource {

	private final DeptMasterService deptMasterService;
	ObjectMapper mapper = new ObjectMapper();
	
	public DeptMasterResource(DeptMasterService deptMasterService) {
		this.deptMasterService = deptMasterService;
	}
	
	@GetMapping("/getAllDepartment")
    public List<DeptMasterDto> getAllDepartment() throws URISyntaxException {
    
		return deptMasterService.getAllDepartment();
    }
	
	@GetMapping("/dept/{id}")
    public DeptMaster getDeptById(@PathVariable("id") Long id) throws URISyntaxException {
		return deptMasterService.getDeptById(id);
    }
	
	@PostMapping("/dept")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public DeptMaster createDept(@Valid @RequestBody DeptMaster dept) throws URISyntaxException {
		return deptMasterService.createDepartment(dept); 
    }
	
	@PutMapping("/dept")
    @Timed
    @PreAuthorize("hasRole(\"" + AuthoritiesConstants.ADMIN + "\")")
    public DeptMaster updateDept(@Valid @RequestBody DeptMaster deptMaster) throws URISyntaxException {
		return deptMasterService.updateDepartment(deptMaster);
    }
	
	@PostMapping("/get/dept/all")
	public Map<String,Object> getAllDepts(@RequestParam("searchDept") String _searchDept,@RequestParam("page") String page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchDept : "+_searchDept);
		DeptMaster search_dept 	= mapper.readValue(_searchDept, DeptMaster.class);
		Integer _page	= mapper.readValue(page, Integer.class);
		search_dept = this.setDeptModel(search_dept);
		System.out.println("AFter : "+_searchDept);
		return deptMasterService.getAllDepartments(search_dept, _page);
	}
    
    @PostMapping("/get/dept/next")
	public List<DeptMaster> nextDept(@RequestParam("searchDept") String _searchDept,@RequestParam("page") String _page) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		System.out.println("_searchDept : "+_searchDept);
		DeptMaster search_dept 	= mapper.readValue(_searchDept, DeptMaster.class);
		Integer page	= mapper.readValue(_page, Integer.class);
		search_dept = this.setDeptModel(search_dept);
		return deptMasterService.nextDepartments(search_dept, page);
	}
    

    public DeptMaster setDeptModel(DeptMaster dept) {
	
		if(dept.getDeptName()!=null && dept.getDeptName().equalsIgnoreCase("")) {
			dept.setDeptName(null);
		}
		
		dept.setIsActive(null);
    	dept.setLastModifiedDate(null);
    	dept.setCreatedDate(null);
    	dept.setCreatedBy(null);
    	dept.setLastModifiedBy(null);
    	dept.setId(null);
    	
		return dept;
	}

}
