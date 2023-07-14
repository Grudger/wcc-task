package com.wcc.assessment.aop;


import com.wcc.assessment.model.audit.WccAudit;
import com.wcc.assessment.model.domain.PostCodeRequest;
import com.wcc.assessment.model.domain.RequestStatus;
import com.wcc.assessment.repository.AuditRepository;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.HashMap;

@Aspect
@Component
public class LocationAudit {

    private final AuditRepository auditRepository;

    public LocationAudit(AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Around("execution(* com.wcc.assessment.service.LocationService.calculateDisplacement(..))")
    public Object aroundDisplacementCalculationAdvice(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Object result;
        HashMap<String, Object> dataMap = new HashMap<>();
        try {
            // in case of success
            result = pjp.proceed();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            dataMap.put("startingPostCode", args[0]);
            dataMap.put("endingPostCode", args[1]);
            dataMap.put("status", RequestStatus.STATUS_SUCCESS);
            WccAudit wccAudit = new WccAudit(Instant.now(), ((User) authentication.getPrincipal()).getUsername(),
                    "Distance Calculation", dataMap, authentication.getName());

            auditRepository.save(wccAudit);

        } catch (Throwable e) {
            // in case of failure
            dataMap.put("status", RequestStatus.STATUS_FAILURE);
            dataMap.put("message", e.getMessage());
            dataMap.put("startingPostCode", args[0]);
            dataMap.put("endingPostCode", args[1]);
            WccAudit wccAudit = new WccAudit(Instant.now(), null,
                    "Distance Calculation", dataMap);
            auditRepository.save(wccAudit);
            throw new RuntimeException(e);
        }
        return result;
    }

    @Around("execution(* com.wcc.assessment.service.LocationService.updatePostCode(..))")
    public Object aroundPostcodeUpdate(ProceedingJoinPoint pjp) {
        Object[] args = pjp.getArgs();
        Object result;
        HashMap<String, Object> dataMap = new HashMap<>();
        try {
            // in case of success
            result = pjp.proceed();
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            dataMap.put("postCode", ((PostCodeRequest) args[0]).getPostcode());
            dataMap.put("status", RequestStatus.STATUS_SUCCESS);
            WccAudit wccAudit = new WccAudit(Instant.now(), ((User) authentication.getPrincipal()).getUsername(),
                    "Update Postcode", dataMap, authentication.getName());

            auditRepository.save(wccAudit);

        } catch (Throwable e) {
            // in case of failure
            dataMap.put("status", RequestStatus.STATUS_FAILURE);
            dataMap.put("message", e.getMessage());
            dataMap.put("postCode", ((PostCodeRequest) args[0]).getPostcode());
            WccAudit wccAudit = new WccAudit(Instant.now(), null,
                    "Update Postcode", dataMap);
            auditRepository.save(wccAudit);
            throw new RuntimeException(e);
        }
        return result;
    }


}
