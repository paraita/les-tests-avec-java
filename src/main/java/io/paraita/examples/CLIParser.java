package io.paraita.examples;

import io.paraita.examples.mail.Mailer;
import io.paraita.examples.mail.SparkpostMailerImpl;
import org.yaml.snakeyaml.Yaml;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;
import java.lang.Integer;

@Command(name = "mailsender", mixinStandardHelpOptions = true, description = "CLI pour envoyer des mails en masse")
public class CLIParser implements Callable<Integer> {

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    @Option(names = {"-c", "--config"}, description = "Fichier de configuration SMTP", required = true)
    protected File config;

    @Option(names = {"-a", "--addresses"}, description = "Fichier contenant la liste des adresses mail", required = true)
    protected File addresses;

    @Option(names = {"-s", "--subject"}, description = "Sujet du message", required = true)
    private String subject;

    @Option(names = {"-m", "--message"}, description = "Corps du message", required = true)
    private String message;

    protected boolean emailAddressIsValid(String address) {
        Boolean result = false;
        if (address.matches("([a-z0-9.\\-_]+)@([a-z0-9\\-_]+)\\.([a-z0-9\\-_]+)")) {
            result = true;
        }
        else {
            System.out.println("Invalid email address: " + address);
        }
        return result;
    }

    protected List<String> getAddressesList() throws IOException {
        return Files.lines(addresses.toPath())
                .filter(address -> emailAddressIsValid(address))
                .collect(Collectors.toList());
    }

    protected Mailer getMailer() throws FileNotFoundException {
        Path configPath = config.toPath();
        InputStream inputStream = new FileInputStream(configPath.toFile());
        System.out.println(config.isAbsolute());
        System.out.println(config.getAbsolutePath());
        Map<String, Object> configFile = new Yaml().load(inputStream);
        Map<String, String> smtpConfig = (Map<String, String>) configFile.get("smtp");
        Mailer mailer = null;

        switch(smtpConfig.get("provider")) {
            case "sparkpost":
                mailer = new SparkpostMailerImpl();
                mailer.configure(Map.of("apiKey", smtpConfig.get("apiKey"),
                        "senderAddress", smtpConfig.get("senderAddress")));
                break;
            default:
        }
        return mailer;
    }

    @Override
    public Integer call() throws Exception {
        List<String> addressesList = getAddressesList();
        Mailer mailer = getMailer();
        mailer.send(addressesList, "Test Paraita", "Corps du message !");
        return 0;
    }

}
