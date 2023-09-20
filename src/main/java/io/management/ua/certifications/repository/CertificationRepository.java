package io.management.ua.certifications.repository;

import io.management.ua.certifications.entity.Certification;
import org.springframework.data.keyvalue.repository.KeyValueRepository;

public interface CertificationRepository extends KeyValueRepository<Certification, String> {
}
