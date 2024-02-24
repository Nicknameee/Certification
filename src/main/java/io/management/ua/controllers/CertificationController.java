package io.management.ua.controllers;

import io.management.ua.certifications.dto.CertificationDTO;
import io.management.ua.certifications.service.CertificationService;
import io.management.ua.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/certification")
@RequiredArgsConstructor
public class CertificationController {
    private final CertificationService certificationService;

    @GetMapping("/mail/allowed")
    public RedirectView certificate(@RequestParam String identifier,
                                    @RequestParam String code,
                                    @RequestParam String origin) {
        certificationService.certificate(CertificationDTO
                .builder()
                .identifier(identifier)
                .code(code)
                .build());
        return new RedirectView(String.format("%s?verified=true", origin));
    }

    @PostMapping("/allowed")
    public Response<?> certificate(@RequestBody @Valid CertificationDTO certificationDTO)  {
        certificationService.certificate(certificationDTO);
        return Response.ok();
    }
}
