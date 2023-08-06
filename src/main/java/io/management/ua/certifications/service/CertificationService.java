package io.management.ua.certifications.service;

import io.management.ua.producers.CertificationResultProducer;
import io.management.ua.utility.certification.models.CertificationDTO;
import io.management.ua.utility.certification.models.CertificationRequestModel;
import io.management.ua.utility.certification.models.CertificationResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationResultProducer certificationResultProducer;

    private static final Map<String, String> certificationCodes = new ConcurrentHashMap<>();

    public void process(CertificationRequestModel certificationRequestModel) {
        String certificationCode = "1111111";

        certificationCodes.put(certificationRequestModel.getIdentifier(), certificationCode);
    }

    public boolean certificate(CertificationDTO certificationDTO) {
        if (certificationCodes.containsKey(certificationDTO.getIdentifier())) {
            if (certificationCodes.get(certificationDTO.getIdentifier()).equals(certificationDTO.getCode())) {
                certificationResultProducer.produce(new CertificationResultModel(certificationDTO.getIdentifier(), true));
                return true;
            }
        }

        return false;
    }
}
