package emp.event_management_platform.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController  implements ErrorController {

    @GetMapping("/error")
    public String handleError() {
        return "error-404";
    }
}