package io.management.ua.controllers;

import io.management.ua.certifications.dto.CertificationDTO;
import io.management.ua.certifications.service.CertificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/api/v1/certification")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping("/mail/allowed")
    public RedirectView certificate(@RequestParam String identifier, @RequestParam String code, @RequestParam String origin) {
        certificationService.certificate(CertificationDTO
                .builder()
                .identifier(identifier)
                .code(code)
                .build());
        return new RedirectView(String.format("%s?signedUp=true", origin));
    }

    @PostMapping("/allowed")
    public ResponseEntity<?> certificate(@RequestBody CertificationDTO certificationDTO)  {
        certificationService.certificate(certificationDTO);
        return ResponseEntity.ok().build();
    }
}
