package io.paraita.examples;

import lombok.Builder;
import lombok.Data;
import org.simplejavamail.api.mailer.Mailer;

import java.util.List;

@Data
@Builder
public class Campagne {
    private List<String> addresses;
    private Mailer mailer;
    private String subject;
    private String message;
}
