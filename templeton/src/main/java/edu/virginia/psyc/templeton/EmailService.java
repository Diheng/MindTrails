package edu.virginia.psyc.templeton;

import org.mindtrails.domain.Participant;
import org.mindtrails.domain.tango.Reward;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;

/**
 * Created by dan on 5/13/16.
 */
@Service
public class EmailService implements org.mindtrails.service.EmailService {

    @Override
    public void sendExportAlertEmail(String message) {
        // Meh.
    }

    @Override
    public void sendPasswordReset(Participant participant) throws MessagingException {
        // Meh.
    }

    @Override
    public void sendGiftCard(Participant participant, Reward reward, int amountCents) throws MessagingException {
        // Meh.
    }


}
