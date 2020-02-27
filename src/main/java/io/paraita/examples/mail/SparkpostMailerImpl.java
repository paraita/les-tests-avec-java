package io.paraita.examples.mail;

import com.sparkpost.Client;
import com.sparkpost.exception.SparkPostException;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SparkpostMailerImpl implements Mailer {

    protected String apiKey;
    protected String senderAddress;
    protected Client client;


    @Override
    public void configure(Map<String, Serializable> config) {
        this.apiKey = ((String) config.get("apiKey"));
        this.senderAddress = ((String) config.get("senderAddress"));
        this.client = new Client(this.apiKey);
    }

    @Override
    public void send(List<String> addresses, String subject, String message) {
        try {
            client.sendMessage(this.senderAddress, addresses, subject, message,null);
        } catch (SparkPostException e) {
            e.printStackTrace();
        }
    }
}
