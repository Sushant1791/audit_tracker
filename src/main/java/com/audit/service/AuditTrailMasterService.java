package com.audit.service;

import java.time.Instant;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.audit.domain.AuditTrailMaster;
import com.audit.domain.User;
import com.audit.repository.AuditTrailMasterRepository;
import com.audit.repository.UserRepository;
import com.audit.security.SecurityUtils;
import com.audit.service.dto.AuditTrailMasterDTO;

@Service
@Transactional
public class AuditTrailMasterService {

	private final AuditTrailMasterRepository auditTrailMasterRepository;
	private final UserRepository userRepository;

	public AuditTrailMasterService(AuditTrailMasterRepository auditTrailMasterRepository,
			UserRepository userRepository) {
		this.auditTrailMasterRepository = auditTrailMasterRepository;
		this.userRepository = userRepository;
	}

	public void saveAuditTrail(AuditTrailMasterDTO auditTrail) {
		AuditTrailMaster master = new AuditTrailMaster();
		Optional<String> loginName = SecurityUtils.getCurrentUserLogin();
		Optional<User> user = loginName.isPresent() ? userRepository.findOneByLogin(loginName.get()) : Optional.empty();
		master.setAuditBy(user.get());
		master.setAuditDate(Instant.now());
		master.setAuditEvent(auditTrail.getAuditEvent());
		master.setAuditType(auditTrail.getAuditType());
		master.setDescription(auditTrail.getDescription());
		auditTrailMasterRepository.save(master);
	}

}
