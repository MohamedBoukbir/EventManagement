package emp.event_management_platform.service;
import emp.event_management_platform.entities.AppPayment;

import java.util.List;

public interface IPaymentService {
    void createPayment(AppPayment payment);

    List<AppPayment> getAllPayments();
}
