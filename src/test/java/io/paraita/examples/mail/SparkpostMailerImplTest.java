package io.paraita.examples.mail;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

class SparkpostMailerImplTest {

    @Mock
    private Client client;

    @InjectMocks
    SparkpostMailerImpl sparkpostMailer;

    @BeforeEach
    void setUp() {
        initMocks(this);
        sparkpostMailer.apiKey = "KEY_VALUE";
        sparkpostMailer.senderAddress = "hello@test.fr";
    }

    @Test
    void testSendValidParameters() {
        List<String> adresses = List.of("haunui@test.fr", "kyle@test.fr");
        sparkpostMailer.send(adresses, "email de test", "Corps du message");
        try {
            verify(client).sendMessage("hello@test.fr", adresses,
                    "email de test", "Corps du message", null);
        } catch (SparkPostException e) {
            e.printStackTrace();
        }
    }
}