package microservices.web_application;

import com.google.gson.Gson;
import org.springframework.core.SpringVersion;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.validation.Valid;
import java.io.*;
import java.net.URL;
import org.springframework.http.HttpMethod;


@Controller
public class MyController {
    RestTemplate restTemplate;

    //Constructor
    public MyController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @PostMapping("/login")
    public String loginPost(@ModelAttribute User user, Model model){
        RestTemplate rt = new RestTemplate();
        Boolean response = rt.postForObject(Config.restUrl + "login", user, Boolean.class);
        if(response){
            Security.access = true;
            return "index";
        }
        model.addAttribute("access", "false");
        return "login";
    }

    @GetMapping("/login")
    public String loginGet(Model model) {
        model.addAttribute("active_nav", "nav_login");
        model.addAttribute("title", "Login");
        return "login";
    }

    //Show home page
    @GetMapping("/index")
    public String index(Model model){
        if(Security.access){
            model.addAttribute("title", "index");
            model.addAttribute("springVersion", "Spring version: "+SpringVersion.getVersion());
            return "index";
        }
        return "login";
    }

    //Show page for delete car by licensenumber
    @GetMapping("/delete")
    public String deleteGet(Model model){
        model.addAttribute("message", "");
        model.addAttribute("active_nav", "nav_delete");
        model.addAttribute("title", "Delete");
        return "delete";
    }

    //Delete car from rest application db by licensenumber
    @PostMapping("/delete")
    public String deletePost(@RequestParam("licensenumber") String licensenumber,Model model) throws IOException {
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> responseEntity = rt.exchange(Config.restUrl + "cars/" + licensenumber, HttpMethod.DELETE, null, String.class);
        String response = responseEntity.getBody();
        String message = "";
        String alert_type = "";
        if (!response.equals("0")) {
            message = "\"" + licensenumber + "\" was successfully deleted from Rest database";
            alert_type = "alert-success";
            System.out.println(message);
        } else {
            message = "\"" + licensenumber + "\" was not found in Rest database";
            alert_type = "alert-warning";
            System.out.println(message);
        }
        model.addAttribute("alert_type", alert_type);
        model.addAttribute("message", message);
        model.addAttribute("active_nav", "nav_delete");
        model.addAttribute("title", "Delete");
        return "delete";
      }

    //Add new car to rest_application db
    @PostMapping("/create")
    public String cratePost(@Valid @ModelAttribute Car car, BindingResult bindingResult, Model model){
        if(!bindingResult.hasErrors()){
            RestTemplate rt = new RestTemplate();
            Boolean response = rt.postForObject(Config.restUrl + "cars", car, Boolean.class);
            String message = "";
            String alert_type = "";
            if(response){
                message = "\""+ car.getLicensenumber() +"\" was successfully added to Rest database";
                alert_type = "alert-success";
                System.out.println(message);
            } else{
                message = "\""+ car.getLicensenumber() +"\" already exist in Rest database";
                alert_type = "alert-warning";
                System.out.println(message);
            }
            model.addAttribute("alert_type", alert_type);
            model.addAttribute("message", message);
            model.addAttribute("active_nav", "nav_create");
            model.addAttribute("title", "Create");
            return "create";
        } else{
            model.addAttribute("title", "Create");
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "error";
        }
    }

    //Show page for adding new car
    @GetMapping("/create")
    public String createGet(Model model){
        model.addAttribute("message", "");
        model.addAttribute("active_nav", "nav_create");
        model.addAttribute("title", "Create");
        return "create";
    }

    //Show page with all cars from rest_application db
    @GetMapping("/cars")
    public String getAllCars(Model model) throws IOException {
        URL oracle = new URL(Config.restUrl + "cars");
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        Car[] cars = new Gson().fromJson(in, Car[].class);
        model.addAttribute("active_nav", "nav_cars");
        model.addAttribute("cars", cars);
        model.addAttribute("title", "Cars");
        return "cars";
    }

    //Search all cars by any field
    @PostMapping("/search")
    public String search(@RequestParam("find") String find, Model model) throws IOException {
        find = find.replaceAll(" ", "_");
        URL oracle = new URL(Config.restUrl + "cars/" + find);
        BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));
        Car[] cars = new Gson().fromJson(in, Car[].class);
        model.addAttribute("find", find);
        model.addAttribute("active_nav", "nav_find");
        model.addAttribute("cars", cars);
        if(cars == null || cars.length<1){
            model.addAttribute("found", 0);
        } else {
            model.addAttribute("found", cars.length);
        }
        model.addAttribute("title", "Find");
        return "search";
    }
}
