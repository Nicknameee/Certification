package io.management.ua.certifications.repository;

import io.management.ua.certifications.entity.Certification;
import org.springframework.data.repository.CrudRepository;

public interface CertificationRepository extends CrudRepository<Certification, String> {
}
