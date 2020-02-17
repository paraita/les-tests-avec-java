package io.paraita.examples.mail;

import lombok.Data;

@Data
public class SparkpostConfig {
    private String apiKey;
    private String senderAddress;
}
