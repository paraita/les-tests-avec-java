package io.paraita.examples;

import io.paraita.examples.mail.Mailer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.matchers.NotNull;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CLIParserTest {

    @Mock
    File config;

    @InjectMocks
    CLIParser cliParser;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetMailerSparkpostProvider() {
        when(config.getAbsolutePath()).thenReturn("config.yaml");
        Mailer res = cliParser.getMailer();
        assertThat(res, is(notNullValue()));
    }

    @Test
    void testEmailAddressIsValid() {
    }

    @Test
    void testGetAddressesList() {
    }
}