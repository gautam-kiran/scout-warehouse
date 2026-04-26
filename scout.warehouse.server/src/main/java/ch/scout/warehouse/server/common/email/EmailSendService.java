package ch.scout.warehouse.server.common.email;

import org.eclipse.scout.rt.mail.CharsetSafeMimeMessage;
import org.eclipse.scout.rt.mail.MailHelper;
import org.eclipse.scout.rt.mail.MailMessage;
import org.eclipse.scout.rt.mail.MailParticipant;
import org.eclipse.scout.rt.mail.smtp.SmtpHelper;
import org.eclipse.scout.rt.mail.smtp.SmtpServerConfig;
import org.eclipse.scout.rt.platform.ApplicationScoped;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.config.CONFIG;

@ApplicationScoped
public class EmailSendService {
  SmtpServerConfig m_smtpServerConfig;

  public void sendEmail(String to, String subject, String body) {
    if (m_smtpServerConfig == null) {
      init();
    }
    MailMessage mailMessage = BEANS.get(MailMessage.class)
      .withSender(BEANS.get(MailParticipant.class).withEmail(CONFIG.getPropertyValue(EmailConfigProperties.EmailFromProperty.class)))
      .addToRecipient(BEANS.get(MailParticipant.class).withEmail(to))
      .withSubject(subject)
      .withBodyHtml(body);
    CharsetSafeMimeMessage mimeMessage = BEANS.get(MailHelper.class).createMimeMessage(mailMessage);
    BEANS.get(SmtpHelper.class).sendMessage(m_smtpServerConfig, mimeMessage);
  }

  private void init(){
    m_smtpServerConfig = BEANS.get(SmtpServerConfig.class)
      .withHost(CONFIG.getPropertyValue(EmailConfigProperties.EmailHostProperty.class))
      .withPort(CONFIG.getPropertyValue(EmailConfigProperties.EmailPortProperty.class))
      .withUsername(CONFIG.getPropertyValue(EmailConfigProperties.EmailFromProperty.class))
      .withPassword(CONFIG.getPropertyValue(EmailConfigProperties.EmailPasswordProperty.class))
      .withUseAuthentication(true)
      .withPoolSize(5)
      .withUseSmtps(true)
      .withUseStartTls(true);
  }
}
