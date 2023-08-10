package io.management.ua.certifications.repository;

import io.management.ua.certifications.entity.CertificationModel;
import org.springframework.data.repository.CrudRepository;

public interface CertificationRepository extends CrudRepository<CertificationModel, String> {
}
