package com.labs.lab5;

import javax.mail.PasswordAuthentication;

public class EmailAuthentication extends javax.mail.Authenticator {
    private String login;
    private String password;

    EmailAuthentication(final String login, final String password)
    {
        this.login    = login;
        this.password = password;
    }
    public PasswordAuthentication getPasswordAuthentication()
    {
        return new PasswordAuthentication(login, password);
    }

}
