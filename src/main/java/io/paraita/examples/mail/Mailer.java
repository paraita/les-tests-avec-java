package io.paraita.examples.mail;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface Mailer {

    void configure(Map<String, Serializable> config);

    void send(List<String> addresses, String subject, String message);

}
