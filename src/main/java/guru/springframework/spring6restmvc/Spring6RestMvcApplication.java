package guru.springframework.spring6restmvc;

import guru.springframework.spring6restmvc.controller.BeerController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

@SpringBootApplication
public class Spring6RestMvcApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Spring6RestMvcApplication.class, args);
        BeerController controller = ctx.getBean(BeerController.class);
        System.out.println(controller.getBeerById(UUID.randomUUID()));
    }

}
