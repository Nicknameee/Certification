package io.management.ua.certifications.service;

import io.management.ua.amqp.messages.CertificationRequestModel;
import io.management.ua.amqp.messages.CertificationResultModel;
import io.management.ua.amqp.messages.MessageModel;
import io.management.ua.certifications.dto.CertificationDTO;
import io.management.ua.certifications.entity.Certification;
import io.management.ua.certifications.repository.CertificationRepository;
import io.management.ua.exceptions.ActionRestrictedException;
import io.management.ua.exceptions.CertificationException;
import io.management.ua.producers.CertificationResultProducer;
import io.management.ua.producers.MessageProducer;
import io.management.ua.utility.CodeGenerator;
import io.management.ua.utility.TimeUtil;
import io.management.ua.utility.models.HttpServletAddressesModel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


@Service
@RequiredArgsConstructor
public class CertificationService {
    private final CertificationRepository certificationRepository;
    private final CertificationResultProducer certificationResultProducer;
    private final MessageProducer messageProducer;
    private final Environment environment;

    @Value("${application.properties.certification.validity}")
    private Long certificationValidity;

    @Transactional
    public void process(CertificationRequestModel certificationRequestModel) {
        String certificationCode = CodeGenerator.generateCode();

        Certification certification = new Certification();
        certification.setIdentifier(certificationRequestModel.getIdentifier());
        certification.setCode(certificationCode);
        certification.setIssuedAt(TimeUtil.getCurrentDateTime());

        certificationRepository.save(certification);

        HttpServletAddressesModel httpServletAddressesModel = certificationRequestModel.getHttpServletAddressesModel();

        MessageModel messageModel = new MessageModel();
        messageModel.setReceiver(certificationRequestModel.getIdentifier());
        messageModel.setSubject("Confirm your action at CRM");

        if (Pattern.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", certificationRequestModel.getIdentifier())) {
            messageModel.setMessageType(MessageModel.MessageType.HTML);

            switch (messageModel.getMessageType()) {
                case HTML -> messageModel.setContent(getCertificationLink(httpServletAddressesModel, getCertificationMessageContent(certificationCode), certificationRequestModel.getIdentifier(), certificationCode));
                case PLAIN_TEXT -> messageModel.setContent(getCertificationMessageContent(certificationCode));
                case WITH_FILE -> throw new ActionRestrictedException("Media files currently not supported");
                default -> throw new ActionRestrictedException("Unknown message type or type was not specified");
            }

            messageModel.setMessagePlatform(MessageModel.MessagePlatform.EMAIL);
        } else {
            messageModel.setMessagePlatform(MessageModel.MessagePlatform.TELEGRAM);
        }

        messageProducer.produce(messageModel);
    }

    @Transactional
    public void certificate(CertificationDTO certificationDTO) {
        Certification certification = certificationRepository.findById(certificationDTO.getIdentifier())
                .orElseThrow(() -> new RuntimeException(String.format("Certification was not found for identifier: %s", certificationDTO.getIdentifier())));

        if (certification.getCode().equals(certificationDTO.getCode())
                && certification
                .getIssuedAt()
                .plusSeconds(certificationValidity)
                .isAfter(TimeUtil.getCurrentDateTime())) {
            certificationRepository.deleteById(certificationDTO.getIdentifier());
            certificationResultProducer.produce(new CertificationResultModel(certificationDTO.getIdentifier(), true));
        } else {
            throw new CertificationException(String.format("Code does not match, identifier: %s",
                    certification.getIdentifier()));
        }
    }

    private String getCertificationMessageContent(String code) {
        return String.format("Your certification code %s is valid for %sH", code, TimeUnit.SECONDS.toHours(certificationValidity));
    }

    private String getCertificationLink(HttpServletAddressesModel httpServletAddressesModel, String message, String identifier, String code) {
        String pattern = "<a href=\"#link?&identifier=#id&code=#code&origin=#redirectTarget\">#message</a>";
        String origin = httpServletAddressesModel.getOrigin();
        String link = String.format("%s://%s:%s/api/v1/certification/mail/allowed",
                httpServletAddressesModel.getOrigin().split(":")[0],
                InetAddress.getLoopbackAddress().getHostName(),
                environment.getProperty("server.port"));

        return pattern
                .replaceAll("#link", link)
                .replaceAll("#id", identifier)
                .replaceAll("#code", code)
                .replaceAll("#message", message)
                .replaceAll("#redirectTarget", origin);
    }

    @Scheduled(fixedRateString = "${application.properties.certification.validity}", timeUnit = TimeUnit.SECONDS)
    public void clearCertificationCache() {
        Iterable<Certification> certifications = certificationRepository.findAll();

        for (Certification certification : certifications) {
            if (TimeUtil.getCurrentDateTime().isAfter(certification.getIssuedAt().plusSeconds(certificationValidity))) {
                certificationRepository.deleteById(certification.getIdentifier());
            }
        }
    }
}
