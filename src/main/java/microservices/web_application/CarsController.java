package microservices.web_application;

import com.google.gson.Gson;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.io.*;
import java.net.URL;


@Controller
public class CarsController {
    RestTemplate restTemplate;

    //Constructor
    public CarsController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    //Show home page
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("title", "index");
        return "index";
    }


    //Show page for delete car by licensenumber
    @GetMapping("/delete")
    public String deleteGet(Model model){
        model.addAttribute("message", "");
        model.addAttribute("active_nav", "nav_delete");
        model.addAttribute("title", "Delete");
        return "delete";
    }




//    Delete car from rest application db by licensenumber
    @PostMapping("/delete")
    public String deletePost(@RequestParam("licensenumber") String licensenumber,Model model) throws IOException {
        System.out.println("\nDELETE\n");
        System.out.println(">>> "+ licensenumber);
        RestTemplate rt = new RestTemplate();
        rt.delete(Config.restUrl + "cars/" + licensenumber);

        //ResponseEntity<String> response = restTemplate.getForEntity(Config.restUrl + "cars/" + licensenumber, String.class);
        String response = "XXXXX";
        System.out.println("<<<<<"+response+">>>>>");

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


        /*String       postUrl       = Config.restUrl+"cars/delete";
        Gson         gson          = new Gson();
        HttpClient httpClient    = HttpClientBuilder.create().build();
        HttpPost     post          = new HttpPost(postUrl);
        StringEntity postingString = new StringEntity(licensenumber);//gson.tojson() converts your pojo to json
        post.setEntity(postingString);
        post.setHeader("Content-type", "text/plain");
        HttpResponse res = httpClient.execute(post);
        HttpEntity entity = res.getEntity();
        boolean response = Boolean.parseBoolean(EntityUtils.toString(entity, "UTF-8"));*/
        return "delete";
    }


    @GetMapping("/login")
    public String form(Model model) {
        model.addAttribute("active_nav", "nav_login");
        model.addAttribute("title", "Login");
        return "login";
    }

//    //Convert object Car to Json format
//    public String carToJson(Car car) throws JsonProcessingException {
//        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
//        String json = ow.writeValueAsString(car);
//        return json;
//    }


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





    //delete
//        Boolean DELETEresponse = rt.delete(Config.restUrl + "cars/add" + licence);



//
//    //Add new car, send json data by POST to rest_application
//    @PostMapping("/create")
//    public String createPost(@RequestParam("licensenumber_input") String licensenumber,
//                      @RequestParam("make_input") String make,
//                      @RequestParam("color_input") String color,
//                      Model model) throws IOException {
//        Car car = new Car(licensenumber, make, color);
//        String postUrl = Config.restUrl+"cars/add";
//        Gson gson = new Gson();
//        HttpClient httpClient = HttpClientBuilder.create().build();
//        HttpPost post = new HttpPost(postUrl);
//        StringEntity postingString = new StringEntity(gson.toJson(car));//gson.tojson() converts your pojo to json
//        post.setEntity(postingString);
//        post.setHeader("Content-type", "application/json");
//        HttpResponse res = httpClient.execute(post);
//        HttpEntity entity = res.getEntity();
//        boolean response = Boolean.parseBoolean(EntityUtils.toString(entity, "UTF-8"));
//        String message = "";
//        String alert_type = "";
//        if(response){
//            message = "\""+ car.getLicensenumber() +"\" was successfully added to Rest database";
//            alert_type = "alert-success";
//            System.out.println(message);
//        } else{
//            message = "\""+ car.getLicensenumber() +"\" already exist in Rest database";
//            alert_type = "alert-warning";
//            System.out.println(message);
//        }
//
//        model.addAttribute("alert_type", alert_type);
//        model.addAttribute("message", message);
//        model.addAttribute("active_nav", "nav_create");
//        model.addAttribute("title", "Test");
//        return "create";
//    }

//    //POST json data by URL
//    public String postToUrl(String json, String url) {
//        final MediaType jsonMediaType = MediaType.parse("application/json");
//        try {
//            RequestBody requestBody = RequestBody.create(jsonMediaType, new Gson().toJson(json));
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(requestBody)
//                    .addHeader("content-type", "application/json")
//                    .build();
//            Response rs = client.newCall(request).execute();
//            String response = rs.body().string();
//            return response;
//        } catch (Exception e) {}
//        return null;
//    }

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
