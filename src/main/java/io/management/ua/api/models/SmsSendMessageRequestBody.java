package io.management.ua.api.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SmsSendMessageRequestBody {
    private String receiver;
    private String text;
}
