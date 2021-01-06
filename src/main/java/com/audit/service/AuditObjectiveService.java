package com.audit.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.domain.AuditProcObjectiveMaster;
import com.audit.domain.AuditProcedureMaster;
import com.audit.domain.User;
import com.audit.repository.AuditObjectiveMasterRepository;
import com.audit.repository.AuditObjectiveRepository;
import com.audit.repository.AuditPlanMasterRepository;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;
import com.audit.service.dto.AuditProcedureMasterDto;
import com.audit.service.dto.AuditProcedureObjectiveDto;
import com.audit.service.mapper.ProcObjectiveMapper;
import com.audit.web.rest.errors.EntityAlreadyUsedException;

@Service
@Transactional
public class AuditObjectiveService {

	private final AuditObjectiveRepository auditObjectiveRepository;
	private final UserRepository userRepository;
	private final AuditObjectiveMasterRepository auditObjectiveMasterRepository;
	private final ProcObjectiveMapper procObjectiveMapper;
	private final AuditPlanMasterRepository auditPlanMasterRepository;
	 
	private final ExampleMatcher search_matcher = ExampleMatcher.matching()
	   		 .withIgnorePaths("isActive")  
				 .withMatcher("procedureTitle", match -> match.ignoreCase().contains())
				 .withMatcher("procedureDescription", match -> match.ignoreCase().contains());

	public AuditObjectiveService(AuditObjectiveRepository auditObjectiveRepository, 
			UserRepository userRepository, AuditObjectiveMasterRepository auditObjectiveMasterRepository,ProcObjectiveMapper procObjectiveMapper,
			AuditPlanMasterRepository auditPlanMasterRepository) {
		this.auditObjectiveRepository = auditObjectiveRepository;
		this.userRepository = userRepository;
		this.auditObjectiveMasterRepository = auditObjectiveMasterRepository;
		this.procObjectiveMapper = procObjectiveMapper;
		this.auditPlanMasterRepository = auditPlanMasterRepository;
	}
	
	public AuditProcedureMaster saveObjective(AuditProcedureMasterDto auditProcedureMaster) {
		Optional<String> loginName=SecurityUtils.getCurrentUserLogin();
		Optional<User> user= loginName.isPresent()?userRepository.findOneByLogin(loginName.get()):Optional.empty();
		AuditProcedureMaster master = procObjectiveMapper.getDomainFromDto(auditProcedureMaster, user.get());
		
		return auditObjectiveRepository.save(master);
	}
	
	public void deleteProcedure(long id) {
		if (auditPlanMasterRepository.findAllByProcedureId(auditObjectiveRepository.findById(id).get()).isEmpty()) {
			auditObjectiveRepository.deleteById(id);
		} else {
			throw new EntityAlreadyUsedException();
		}
	}
	
	public void deleteObjectie(long id) {
		auditObjectiveMasterRepository.deleteById(id);
	}
	
	public List<AuditProcedureMasterDto> getAllObjective(){
		
		List<AuditProcedureMaster> auditProcedureMasters= auditObjectiveRepository.findAll();
		
		return procObjectiveMapper.ProcedureMasterToProcedureMasterDTOs(auditProcedureMasters);
	}
	
	public List<AuditProcedureMasterDto> findByProcedureTitleOrProcedureDescription(String title,String description) {
		List<AuditProcedureMaster> auditProcedureMasters= auditObjectiveRepository.findByProcedureTitleOrProcedureDescription(title, description);
		
		return procObjectiveMapper.ProcedureMasterToProcedureMasterDTOs(auditProcedureMasters);
	}
	
	public void setActiveDeactiveProcedure(Long id, boolean active) {
		AuditProcedureMaster auditProcedureMaster=auditObjectiveRepository.getOne(id);
		auditProcedureMaster.setActive(active);
		auditObjectiveRepository.save(auditProcedureMaster);
	}
	
	public void setActiveDeactiveObjective(Long id, boolean active) {
		AuditProcObjectiveMaster auditObjectiveMaster=auditObjectiveMasterRepository.getOne(id);
		auditObjectiveMaster.setActive(active);
		auditObjectiveMasterRepository.save(auditObjectiveMaster);
	}
	
	public void updateObjective(AuditProcedureObjectiveDto auditProcedureObjectiveDto) {
		AuditProcObjectiveMaster master = auditObjectiveMasterRepository.getOne(auditProcedureObjectiveDto.getId());
		master.setObjectiveDescription(auditProcedureObjectiveDto.getObjectiveDescription());
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()) : Optional.empty();
		master.setObjectiveName(auditProcedureObjectiveDto.getObjectiveName());
		master.setLastModifiedDate(Instant.now());
		master.setLastModifiedBy(user.get());
		auditObjectiveMasterRepository.save(master);
	}
	
	public AuditProcedureMasterDto getProcedureById(long id) {
		Optional<AuditProcedureMaster> auditProcedureMaster= auditObjectiveRepository.findById(id);
		if(auditProcedureMaster.isPresent()) {
			return procObjectiveMapper.getDTOFromDomain(auditProcedureMaster.get());
		}
		return null;
	}

	public void bulkUpdateAuditEntities(List<AuditProcedureMasterDto> auditEntities) {
		auditEntities.stream().forEach(auditEntity -> saveObjective(auditEntity));
		
	}
	
	@SuppressWarnings("deprecation")
	public Map<String, Object> getIntialObjectives(AuditProcedureMaster searchProcedure, Integer page) {
		Map<String, Object> map = new HashMap<>();
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC,"procedureTitle");
		map.put("list", procObjectiveMapper.ProcedureMasterToProcedureMasterDTOs(
				auditObjectiveRepository.findAll(Example.of(searchProcedure, search_matcher), ten).getContent()));
		map.put("count", this.getCount(searchProcedure));

		return map;
	}

	public Integer getCount(AuditProcedureMaster searchProcedure) {
		Integer count = 0;
		List<AuditProcedureMaster> all = auditObjectiveRepository.findAll(Example.of(searchProcedure, search_matcher));
		if (!all.isEmpty())
			count = all.size();
		return count;
	}

	@SuppressWarnings("deprecation")
	public List<AuditProcedureMasterDto> nextObjectives(AuditProcedureMaster searchProcedure, Integer page) {
		Pageable ten = new PageRequest(page, 10, Sort.Direction.DESC,"procedureTitle");
		return procObjectiveMapper.ProcedureMasterToProcedureMasterDTOs(
				auditObjectiveRepository.findAll(Example.of(searchProcedure, search_matcher), ten).getContent());
	}
	
}
