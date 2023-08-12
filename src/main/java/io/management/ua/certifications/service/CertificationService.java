package io.management.ua.certifications.service;

import io.management.ua.amqp.models.messages.CertificationRequestModel;
import io.management.ua.amqp.models.messages.CertificationResultModel;
import io.management.ua.amqp.models.messages.MessageModel;
import io.management.ua.certifications.dto.CertificationDTO;
import io.management.ua.certifications.entity.Certification;
import io.management.ua.certifications.repository.CertificationRepository;
import io.management.ua.exceptions.CertificationException;
import io.management.ua.producers.CertificationResultProducer;
import io.management.ua.producers.MessageProducer;
import io.management.ua.utility.CodeGenerator;
import io.management.ua.utility.TimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CertificationResultProducer certificationResultProducer;
    private final MessageProducer messageProducer;

    @Transactional
    public void process(CertificationRequestModel certificationRequestModel) {
        String certificationCode = CodeGenerator.generateCode();

        Certification certification = new Certification();
        certification.setIdentifier(certificationRequestModel.getIdentifier());
        certification.setCode(certificationCode);
        certification.setIssuedAt(TimeUtil.getCurrentDateTime());

        certificationRepository.save(certification);

        MessageModel messageModel = new MessageModel();
        messageModel.setReceiver(certificationRequestModel.getIdentifier());
        messageModel.setContent(getCertificationMessageContent(certificationCode));

        messageProducer.produce(messageModel);
    }

    @Transactional
    public void certificate(CertificationDTO certificationDTO) {
        Certification certification = certificationRepository.findById(certificationDTO.getIdentifier())
                .orElseThrow(() -> new RuntimeException(String.format("Certification was not found for identifier: %s", certificationDTO.getIdentifier())));

        if (certification.getCode().equals(certificationDTO.getCode()) && certification.getIssuedAt().plusHours(1L).isAfter(TimeUtil.getCurrentDateTime())) {
            certificationRepository.deleteById(certificationDTO.getIdentifier());
            certificationResultProducer.produce(new CertificationResultModel(certificationDTO.getIdentifier(), true));
        } else {
            throw new CertificationException(String.format("Code does not match, identifier: %s", certification.getIdentifier()));
        }
    }

    private String getCertificationMessageContent(String code) {
        return String.format("Your certification code %s", code);
    }

    public void clearCertificationCache() {
        Iterable<Certification> certifications = certificationRepository.findAll();

        for (Certification certification : certifications) {
            if (!certification.getIssuedAt().plusHours(1L).isAfter(TimeUtil.getCurrentDateTime())) {
                certificationRepository.deleteById(certification.getIdentifier());
            }
        }
    }
}
