package emp.event_management_platform.service;
import emp.event_management_platform.entities.AppPayment;

public interface IPaymentService {
    void createPayment(AppPayment payment);
}
