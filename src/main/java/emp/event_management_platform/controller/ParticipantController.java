package emp.event_management_platform.controller;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import emp.event_management_platform.entities.AppPayment;
import emp.event_management_platform.entities.AppUser;
import emp.event_management_platform.entities.Event;
import emp.event_management_platform.entities.PaymentStatus;
import emp.event_management_platform.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@AllArgsConstructor
@Slf4j
@Transactional
public class ParticipantController {
    private IParticipant participant;
    private IEvent iEvent;
    private IEmailSend iEmailSend;
    private final IPaypalService paypalService;
    private IPaymentService paymentService;



@GetMapping("user/register/event")
    public RedirectView  registerForEvent(@RequestParam Long id,  RedirectAttributes redirectAttributes, HttpSession session){
    String response;
    AppUser user = participant.getUserAuth();
    Event event = iEvent.getEventById(id);

    session.setAttribute("eventId", id);

    if (event.getParticipants().contains(user)) {
         response = participant.cancelRegistration(user, event);
//        sendNotification("Cancellation from Event", response.split(":")[0]);
        redirectAttributes.addFlashAttribute("message", response);
        return new RedirectView("/user/event/getAll");
    }else{
        System.out.println("Registration for event");
        log.info("Registration for event: Starting payment process.");
        return createPayment("paypal", event.getPrice(), "USD", "Registration for event");
    }

    }

    public RedirectView createPayment(String method, Double amount, String acurrency, String description
    ){
    System.out.println("inside createPayment: " + method);
        try {
            String cancelUrl="http://localhost:8089/payment/cancel";
            String successUrl="http://localhost:8089/payment/success";
            Payment payment = paypalService.createPayment(Double.valueOf(amount),acurrency,
                    method,"sale",description
                    ,cancelUrl,successUrl);

            for (Links links: payment.getLinks()){
                if (links.getRel().equals("approval_url")){
                    return  new RedirectView(links.getHref());

                }
            }

        }catch (PayPalRESTException p){
            log.error("Error occurred ",p);
        }
        return  new RedirectView("/payment/error");
    }

    @GetMapping("/payment/success")
    public String paymentSuccess(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID" ) String  payerId,
            HttpSession session

    ){

        try{
            Payment payment =paypalService.executePayment(paymentId,payerId);
            if(payment.getState().equals("approved")){
                String response;
                Long id = (Long) session.getAttribute("eventId");
                AppUser user = participant.getUserAuth();
                Event event = iEvent.getEventById(id);
                response = participant.registerForEvent(user, event);
                AppPayment appPayment =new AppPayment();
                appPayment.setAmount(event.getPrice());
                appPayment.setEvent(event);
                appPayment.setDate(LocalDate.from(LocalDateTime.now()));
                appPayment.setStatus(PaymentStatus.COMPLETED);
                appPayment.setParticipant(user);
                paymentService.createPayment(appPayment);
                session.removeAttribute("eventId");
                sendNotification("Participation in Event", response.split(":")[0]);
                return "/payments/paymentSuccess";
            }

        }catch (PayPalRESTException p){
            log.error("Error occurred ",p);
        }
        return "/payments/paymentSuccess";
    }

    @GetMapping("/payment/cancel")
    public String paymentCancel(){
        return "/payments/paymentCancel";
    }

    @GetMapping("/payment/error")
    public String paymentError(){
        return "/payments/paymentError";
    }

    public  void  sendNotification(String subject, String body ){
        AppUser user = participant.getUserAuth();
        iEmailSend.sendEmail(user.getEmail(), subject,body);
    }
    @GetMapping("user/waiting/event")
    public RedirectView registerWaitingForEvent(@RequestParam Long id,  RedirectAttributes redirectAttributes){
       String response;
        AppUser user = participant.getUserAuth();
        Event event = iEvent.getEventById(id);
        if (!event.getWaiting_list().contains(user)) {
       response= participant.registerWaitingForEvent(user,event);
//       sendNotification("Waiting list   Event", response.split(":")[0]);
        redirectAttributes.addFlashAttribute("message", response);
        return new RedirectView("/user/event/getAll");}
        return new RedirectView("/user/event/getAll");

    }




}
