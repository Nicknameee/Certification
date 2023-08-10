package io.management.ua.controllers;

import io.management.ua.certifications.service.CertificationService;
import io.management.ua.utility.certification.models.CertificationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/allowed/v1/certification")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @PostMapping
    public ResponseEntity<?> certificate(@RequestBody CertificationDTO certificationDTO)  {
        certificationService.certificate(certificationDTO);
        return ResponseEntity.ok().build();
    }
}
