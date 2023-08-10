package io.management.ua.certifications.service;

import io.management.ua.certifications.entity.CertificationModel;
import io.management.ua.certifications.repository.CertificationRepository;
import io.management.ua.producers.CertificationResultProducer;
import io.management.ua.utility.CodeGenerator;
import io.management.ua.utility.certification.models.CertificationDTO;
import io.management.ua.utility.certification.models.CertificationRequestModel;
import io.management.ua.utility.certification.models.CertificationResultModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CertificationResultProducer certificationResultProducer;

    public void process(CertificationRequestModel certificationRequestModel) {
        String certificationCode = CodeGenerator.generateCode();

        CertificationModel certificationModel = new CertificationModel();
        certificationModel.setIdentifier(certificationRequestModel.getIdentifier());
        certificationModel.setCode(certificationCode);

        certificationRepository.save(certificationModel);
    }

    public void certificate(CertificationDTO certificationDTO) {
        CertificationModel certificationModel = certificationRepository.findById(certificationDTO.getIdentifier())
                .orElseThrow(() -> new RuntimeException(String.format("Certification was not found for identifier: %s", certificationDTO.getIdentifier())));

        if (certificationModel.getCode().equals(certificationDTO.getCode())) {
            certificationRepository.deleteById(certificationDTO.getIdentifier());
            certificationResultProducer.produce(new CertificationResultModel(certificationDTO.getIdentifier(), true));
        } else {
            certificationResultProducer.produce(new CertificationResultModel(certificationDTO.getIdentifier(), false));
        }
    }
}
