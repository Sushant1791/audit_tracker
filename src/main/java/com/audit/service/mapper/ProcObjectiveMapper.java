package com.audit.service.mapper;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.domain.AuditProcObjectiveMaster;
import com.audit.domain.AuditProcedureMaster;
import com.audit.domain.User;
import com.audit.repository.AuditObjectiveRepository;
import com.audit.repository.CostCentreRepository;
import com.audit.repository.DeptMasterRepository;
import com.audit.service.dto.AuditProcedureMasterDto;

@Service
@Transactional
public class ProcObjectiveMapper {
	
	private final CostCentreRepository costCentreRepository;
	private final DeptMasterRepository deptMasterRepository;
	private final AuditObjectiveRepository auditObjectiveRepository;
	

	public ProcObjectiveMapper(AuditObjectiveRepository auditObjectiveRepository,CostCentreRepository costCentreRepository,DeptMasterRepository deptMasterRepository) {
		this.costCentreRepository = costCentreRepository;
		this.deptMasterRepository = deptMasterRepository;
		this.auditObjectiveRepository = auditObjectiveRepository;
		
	}
	
	
	public AuditProcedureMasterDto getDTOFromDomain(AuditProcedureMaster auditProcedureMaster) {
		return new AuditProcedureMasterDto(auditProcedureMaster);
	}
	
	  public List<AuditProcedureMasterDto> ProcedureMasterToProcedureMasterDTOs(List<AuditProcedureMaster> auditProcedureMasterList) {
	        return auditProcedureMasterList.stream()
	            .filter(Objects::nonNull)
	            .map(this::getDTOFromDomain)
	            .collect(Collectors.toList());
	    }
	  
	  
	  public AuditProcedureMaster getDomainFromDto(AuditProcedureMasterDto auditProcedureMasterDto, User user) {
		  AuditProcedureMaster auditProcedureMaster;
		  if(auditProcedureMasterDto.getId()!=null && auditProcedureMasterDto.getId()!=0) {
			  auditProcedureMaster = auditObjectiveRepository.findById(auditProcedureMasterDto.getId()).get();
		  }else {
			  auditProcedureMaster = new AuditProcedureMaster();
			  auditProcedureMaster.setId(auditProcedureMasterDto.getId());  
		  }
		  
		  
		  auditProcedureMaster.setActive(true);
		  auditProcedureMaster.setCostCentreId(costCentreRepository.getOne(auditProcedureMasterDto.getCostCentreId()));
		  auditProcedureMaster.setDeptId(deptMasterRepository.getOne(auditProcedureMasterDto.getDeptartment()));
		  
		if (null != auditProcedureMasterDto.getId()) {
			auditProcedureMaster.setLastModifiedBy(user);
			auditProcedureMaster.setLastModifiedDate(Instant.now());
		}else {
			auditProcedureMaster.setLastModifiedBy(user);
			auditProcedureMaster.setLastModifiedDate(Instant.now());
			auditProcedureMaster.setCreatedBy(user);
			auditProcedureMaster.setCreatedDate(Instant.now());
		}
		
		
		  auditProcedureMaster.setProcedureDescription(auditProcedureMasterDto.getProcedureDescription());
		  auditProcedureMaster.setProcedureTitle(auditProcedureMasterDto.getProcedureTitle());
		  
		  if (null == auditProcedureMasterDto.getId() || auditProcedureMasterDto.getId() == 0) {
			  List<AuditProcObjectiveMaster> auditProcObjectiveMasters=new ArrayList<>();
				 auditProcedureMasterDto.getAuditProcedureObjectiveData().forEach(
							  auditProcedureObjectiveDto -> {
								  AuditProcObjectiveMaster auditProcObjectiveMaster=new AuditProcObjectiveMaster();  
								  auditProcObjectiveMaster.setActive(true);
								  auditProcObjectiveMaster.setCreatedBy(user);
								  auditProcObjectiveMaster.setCreatedDate(Instant.now());
								  auditProcObjectiveMaster.setLastModifiedBy(user);
								  auditProcObjectiveMaster.setLastModifiedDate(Instant.now());
								  auditProcObjectiveMaster.setObjectiveDescription(auditProcedureObjectiveDto.getObjectiveDescription());
								  auditProcObjectiveMaster.setObjectiveName(auditProcedureObjectiveDto.getObjectiveName());
								  auditProcObjectiveMaster.setProcedureId(auditProcedureMaster);
								  auditProcObjectiveMasters.add(auditProcObjectiveMaster);
						  });
				 auditProcedureMaster.setAuditProcObjectiveMasterCollection(auditProcObjectiveMasters);  
		  }
		  
		  
		  
		  return auditProcedureMaster;
	  }
}
