package io.management.ua.controllers;

import io.management.ua.certifications.dto.CertificationDTO;
import io.management.ua.certifications.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/certification")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping("/mail/allowed")
    public ResponseEntity<?> certificate(@RequestParam String identifier, @RequestParam String code) {
        certificationService.certificate(CertificationDTO
                .builder()
                .identifier(identifier)
                .code(code)
                .build());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/allowed")
    public ResponseEntity<?> certificate(@RequestBody CertificationDTO certificationDTO)  {
        certificationService.certificate(certificationDTO);
        return ResponseEntity.ok().build();
    }
}
