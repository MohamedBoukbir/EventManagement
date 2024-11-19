package emp.event_management_platform.service;

public interface IEmailSend {
   void  sendEmail(String to , String subject, String body);
}
