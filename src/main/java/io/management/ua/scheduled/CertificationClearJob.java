package io.management.ua.scheduled;

import io.management.ua.certifications.service.CertificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class CertificationClearJob {
    private final CertificationService certificationService;

    @Scheduled(fixedRateString = "${application.properties.certification.validity}", timeUnit = TimeUnit.SECONDS)
    public void clearCertificationCache() {
        certificationService.clearCertificationCache();
    }
}
