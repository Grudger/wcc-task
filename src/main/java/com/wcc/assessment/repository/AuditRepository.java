package com.wcc.assessment.repository;

import com.wcc.assessment.model.audit.WccAudit;
import org.springframework.data.repository.CrudRepository;

public interface AuditRepository extends CrudRepository<WccAudit, String> {



}
