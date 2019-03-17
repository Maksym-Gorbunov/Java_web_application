package microservices.web_application;

//Helping config to handle url, easy to change port
//For another port change here and in application.properties
public class Config {

    //RUN LOCAL FROM JAR FILE FOR ME
    //public static String port = "http://172.19.117.145:3000/";
    //public static String restUrl = "http://172.19.117.145:4000/";


    //RUN LOCAL DEFAULT FROM 2 JAR FILES
    public static String port = "http://localhost:3000/";
    public static String restUrl = "http://localhost:4000/";


    //RUN LOCAL FOR ME rest_application from Jar and web_application from DOCKER
    //java -jar rest_application:v0.0.1
    //docker run -p 80:3000 web_application:v0.0.1
    //OBS CAN RUN ONLY 1 DOCKER LOCAL REST OR WEB both working
//    public static String port = "http://172.19.117.145:3000/";
//    public static String restUrl = "http://172.19.117.145:4000/";
//    public static String port = "http://10.0.75.1/";







}
