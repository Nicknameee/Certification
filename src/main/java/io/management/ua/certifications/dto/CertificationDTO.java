package io.management.ua.certifications.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CertificationDTO {
    @NotBlank(message = "Identifier can not be blank")
    private String identifier;
    @NotBlank(message = "Code can not be black")
    private String code;
}
