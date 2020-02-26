package io.paraita.examples;

import io.paraita.examples.mail.Mailer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.yaml.snakeyaml.error.YAMLException;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class CLIParserTest {

    // flaky
    @Mock
    File config;

    // flaky
    @Mock
    File addresses;

    @InjectMocks
    CLIParser cliParser;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @Test
    void testGetMailerFileDoesntExist() {
        when(config.getAbsolutePath()).thenReturn("config-existe-pas.yaml");
        Assertions.assertThrows(YAMLException.class, () -> {
            cliParser.getMailer();
        });
    }

    @Test
    void testGetMailerSparkpostProvider() {
        when(config.getAbsolutePath()).thenReturn("config-sparkpost.yaml");
        assertThat(cliParser.getMailer(), is(notNullValue()));
    }

    @Test
    void testGetMailerGmailProviderIsNotSupported() {
        when(config.getAbsolutePath()).thenReturn("config-gmail.yaml");
        assertThat(cliParser.getMailer(), is(nullValue()));
    }

    @ParameterizedTest(name = "{0} is a valid address")
    @ValueSource(strings = {"toto@tata.titi", "toto.tata@titi.pf", "toto-tata@titi.pf", "hello_123@titi.pf"})
    void testEmailAddressIsValidWithValidAddress(String emailAddress) {
        assertThat(cliParser.emailAddressIsValid(emailAddress), is(true));
    }

    @ParameterizedTest(name = "{0} is an invalid address")
    @ValueSource(strings = {"hello@toto,pf", "hey@localhost", "h@h@@titi.pf", "hey @titi.pf"})
    void testEmailAddressIsValidWithInvalidAddress(String emailAddress) {
        assertThat(cliParser.emailAddressIsValid(emailAddress), is(false));
    }

    @Test
    void testGetAddressesListWith5ValidAddresses() {
        Path path = FileSystems.getDefault().getPath("src", "test", "resources",
                "5_adresses_email_valides.txt");
        when(addresses.toPath()).thenReturn(path);
        assertThat(cliParser.getAddressesList().size(), is(5));
    }

    @Test
    void testGetAddressesListWith4ValidAnd1InvalidAddresses() {
        Path path = FileSystems.getDefault().getPath("src", "test", "resources",
                "4_adresses_email_valides_1_invalide.txt");
        when(addresses.toPath()).thenReturn(path);
        assertThat(cliParser.getAddressesList().size(), is(4));
    }

    @Test
    void testGetAddressesListFileDoesntExist() {
        fail();
    }

    @Test
    void testGetAddressesListEmptyFile() {
        fail();
    }

}