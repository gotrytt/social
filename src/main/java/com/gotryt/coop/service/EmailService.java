package com.gotryt.coop.service;

import com.gotryt.coop.dto.EmailDetails;

public interface EmailService {

    void sendEmail(EmailDetails emailDetails);
    void sendEmailAttach(EmailDetails emailDetails);
}
