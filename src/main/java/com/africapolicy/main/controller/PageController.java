package com.africapolicy.main.controller;

import com.africapolicy.main.DTO.*;
import com.africapolicy.main.config.CodeConfig;
import com.africapolicy.main.entity.*;
import com.africapolicy.main.entity.VerificationEntity;
import com.africapolicy.main.repo.*;
import com.africapolicy.main.response.StatusMessage;
import com.africapolicy.main.email.ScheduleEmailRequest;
import com.africapolicy.main.service.EmailService;
import com.africapolicy.main.service.RoomReservationService;
import com.africapolicy.main.service.SmsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.gson.Gson;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import net.minidev.json.JSONObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author Olalekan Folayan
 */
@Controller
public class PageController {
    @Autowired
    RoomRepo roomRepo;


    @Value("${dhis-url}")
    private String dhisurl;


    @Value("${lagos-payment-url}")
    private String lagosurl;


    @Value("${lagos-payment-api}")
    private String lagospaymentapi;

    @Value("${dhis-username}")
    private String dhisusernam;

    @Value("${dhis-password}")
    private String dhispassword;

    @Value("${sendgridapi}")
    private String sendgridapi;


    WebClient webClient;


    @Autowired
    UserProfileRepo userProfileRepo;



    @Autowired
    ReservationRepo reservationRepo;

    @Autowired
    HealthCenterRepo healthCenterRepo;


    @Autowired
    ServletContext context;

    @Autowired
    EmailService emailservice;

    @Autowired
    SmsService smsService;



    @Autowired
    private RoomReservationService roomReservationService;




    /* name of config file with token image  specifications */
    @Value("${applicationurl}")
    private String applicationurl;



    @RequestMapping(value = "/verification-guide", method = RequestMethod.GET)
    public String gotoguide(Model model) {
        System.out.println("show guide");
        return "guide";
    }

    @RequestMapping(value = "/vaccination-sites", method = RequestMethod.GET)
    public String gotoguidee(Model model) {
        System.out.println("show guide");
        //verificationService.getUserByIdAsync();



        model.addAttribute("userprofile", new UserProfile());


//        Role userRole = roleRepo.findByRole("Admin");
//        User newUser= new User();
//
//        newUser.setName("Admin");
//        newUser.setEmail("admin@nphcda.gov.ng");
//        newUser.setPhone("07036273607");
//        newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//
//        newUser.setPassword(bCryptPasswordEncoder.encode("adminJ4b"));
//        newUser.setActive(1);
//        userRepo.save(newUser);

        List<String> stateList= healthCenterRepo.findDistinctAll("public");


        System.out.println(stateList.toString());



        model.addAttribute("states", stateList);
        return "vacsites";
    }




    @RequestMapping(value = "/paymentconfirmation", method = RequestMethod.GET)
    public String gotoguide(Model model, @RequestParam(name = "id", required = true) String vacid, @RequestParam(name = "tid", required = true) String tid ) {
        System.out.println("show guide");

        UserProfile userp= userProfileRepo.findByPaymentid(vacid);

        userp.setTransid(tid);
        userp.setPaid("Paid");

        model.addAttribute("userProfile", userp);
        return "result";

    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String homePage(Model model) {


        //verificationService.getUserByIdAsync();



        model.addAttribute("userprofile", new UserProfile());


//        Role userRole = roleRepo.findByRole("Admin");
//        User newUser= new User();
//
//        newUser.setName("Admin");
//        newUser.setEmail("admin@nphcda.gov.ng");
//        newUser.setPhone("07036273607");
//        newUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
//
//        newUser.setPassword(bCryptPasswordEncoder.encode("adminJ4b"));
//        newUser.setActive(1);
//        userRepo.save(newUser);

        List<String> stateList= healthCenterRepo.findDistinctAll("public");


        System.out.println(stateList.toString());



        model.addAttribute("states", stateList);

        return "nphcda-ereg";
    }


    @RequestMapping(value = "/senddigicetf", method = RequestMethod.POST)
    public ResponseEntity<StatusMessage> senddigitalcertf(@Valid VerificationEntity verificationEntity, BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session) throws ParseException {


        TrackedEntityInstance credentialsdetails = (TrackedEntityInstance) session.getAttribute("mycredentials");
        model.addAttribute("vaccinationnumber", credentialsdetails.getAttributes().get(0).getValue());
        model.addAttribute("surname", credentialsdetails.getAttributes().get(1).getValue());
        model.addAttribute("gender", credentialsdetails.getAttributes().get(2).getValue());
        model.addAttribute("age", calculateAgeWithJava7(credentialsdetails.getAttributes().get(3).getValue()));
        model.addAttribute("firstname", credentialsdetails.getAttributes().get(4).getValue());
        model.addAttribute("barcode", "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data="+applicationurl+"confirm?vackey=" );

        ScheduleEmailRequest welcomemail = new ScheduleEmailRequest();
        welcomemail.setEmail(verificationEntity.getVerificationEmail());
        welcomemail.setSubject("COVID-19 Digital certficate- NPHCDA");
        welcomemail.setName(credentialsdetails.getAttributes().get(1).getValue() + " " + credentialsdetails.getAttributes().get(4).getValue());
        welcomemail.setBody("" +
                "<p> Vaccination ID -" + credentialsdetails.getAttributes().get(0).getValue() + "</p>" +
                "<p> Gender -" + credentialsdetails.getAttributes().get(2).getValue() + "</p>" +
                "<p> Age - " + calculateAgeWithJava7(credentialsdetails.getAttributes().get(3).getValue())+ "</p>" +
                "<p> Status- NOT COMPLETE</p>" +
                "<p style='text-align: center;'> <img  src=" + "https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=" + applicationurl + "confirm?vackey=</p>");

        welcomemail.setTemplate("ecertf.html");

        emailservice.scheduleEmail(welcomemail);

        return  ResponseEntity.ok(new StatusMessage("success", "great"));
    }












    public int calculateAgeWithJava7(String birthDate) throws ParseException {
        // validate inputs ...
        DateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date date1=new SimpleDateFormat("yyyyMMdd").parse(birthDate);
        int d1 = Integer.parseInt(formatter.format(date1));
        int d2 = Integer.parseInt(formatter.format(new Date()));
        int age = (d2 - d1) / 10000;
        return age;
    }




    @RequestMapping(value = "/confirmation", method = RequestMethod.GET)
    public String homeConfisdcmation(Model model, HttpSession session) {


        return "redirect:/confirm?vackey=" + session.getAttribute("vackey");
    }

    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public String confirmationPage(Model model, HttpSession session, @RequestParam(name = "vackey", required = true) String vackey){

       UserProfile userp= userProfileRepo.findByVac(vackey);

       model.addAttribute("userProfile", userp);
        return "result";
    }


    String errorMessage;
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity<StatusMessage> createNewUser(@Valid UserProfile user, BindingResult bindingResult, Model model, HttpServletRequest request, HttpSession session) throws ParseException, URISyntaxException, JsonProcessingException {

         if(user.getGender().equalsIgnoreCase("female") && user.getPregnant().equalsIgnoreCase("true")){

            System.out.println("error");
            errorMessage="Pregnant women are not eligible for vaccination at this time. ";
            return  ResponseEntity.status(500).body(new StatusMessage("error",errorMessage ));
          }



        if(validatePhoneNumber(user.getPhone(), user.getPhoneconf(), user)){
            UserProfile userProfile = new UserProfile();
            userProfile.setSurname(user.getSurname());
            userProfile.setFirstname(user.getFirstname());
            userProfile.setEmail(user.getEmail());
            userProfile.setGender(user.getGender());
            userProfile.setPhone(user.getPhone());
            userProfile.setOthername(user.getOthername());
            userProfile.setDateofbirth(user.getDateofbirth());
            userProfile.setEstimatedage(user.getEstimatedage());
            userProfile.setResaddress(user.getResaddress());
            userProfile.setPregnant(user.getPregnant());
            userProfile.setStateresidence(user.getStateresidence());
            userProfile.setIdtype(user.getIdtype());
            userProfile.setLga(user.getLga());
            System.out.println("fac"+user.getHealthfacility());
            Healthcenter hc= healthCenterRepo.findByHealthCenterId(user.getFacilityid());
            userProfile.setKnowdob(user.getKnowdob());

            userProfile.setHealthfacility(hc.getHealthCenter());
            userProfile.setOrgid(hc.getOrganizationuit());

            userProfile.setDate(user.getDate());
            userProfile.setSession(user.getSession());
            userProfile.setFacilitytype(user.getFacilitytype());

            String vackey= generator();

            userProfile.setVac(vackey);
            if(user.getDosetype()=="first"){

            }else{
                userProfile.setCost(hc.getFulldose());
            }


            if(user.getFacilitytype()=="public"){
                userProfile.setPaid("paid");
            }else{

                userProfile.setPaid("unpaid");
            }





            String packae;





            if(user.getDosetype().equalsIgnoreCase("first")){
                userProfile.setCost(hc.getFulldose());
                packae="Standard";
                System.out.println("first dose");
                System.out.println(user.getFacilitytype());
            }else{
                packae="Premium";
                userProfile.setCost(hc.getOnedose());
                System.out.println(user.getFacilitytype());
                System.out.println("second dose");
            }









           String shortId = RandomStringUtils.randomAlphabetic(5);

            userProfile.setPaymentid(shortId);




            if(user.getStateresidence().equalsIgnoreCase("Lagos State") && user.getFacilitytype().equalsIgnoreCase("private")){
                LagosPaymentResponse re= updatePayment(userProfile,packae, hc.getOrganizationuit());

                System.out.println("Initialising");
                System.out.println(re.toString());
                userProfile.setPaymentref(re.getPayment_url());


            }
            userProfileRepo.save(userProfile);

            updateToDHIS(userProfile);







            Reservation myReservation= new Reservation();
            Date reservationdate=new SimpleDateFormat("yyyy-MM-dd").parse(user.getDate());
            myReservation.setDate(new java.sql.Date(reservationdate.getTime()));
            System.out.println("fac"+user.getFacilityid());
            System.out.println("hi"+ healthCenterRepo.findByHealthCenterId(user.getFacilityid()));

            Room myRoom= roomRepo.findByHealthCenterIdAndTimeslot(user.getFacilityid(), user.getSession());
            System.out.println(myRoom.toString());
            myReservation.setRoomId(myRoom.getId());
            myReservation.setTimeslot(user.getSession());
            myReservation.setUserId(userProfile.getUserId());


            reservationRepo.save(myReservation);


            Email from = new Email("noreply@nphcda.gov.ng");
            String subject = "COVID-19 registration update";
            Email to = new Email(userProfile.getEmail());
            String timeing;

            if(myRoom.getTimeslot().equalsIgnoreCase("Morning")){

                timeing="(8:00am -12:00pm)";
            }else{
                timeing="(12:00 noon -3:00pm)";
            }

            Content content = new Content("text/html", "<p> Dear " + userProfile.getSurname() + " "+userProfile.getFirstname() + " "+ userProfile.getOthername() +", </p>" +
                    "<p> Confirmation of your vaccination schedule request </p>"+
                    "<p> Vaccination ID -"+ userProfile.getVac() + "</p>" +
                    "<p> Health Facility -"+hc.getHealthCenter() +"-"+ hc.getOrganizationuit()+"</p>" +
                   "<p>  Schedule Session - "+ myRoom.getTimeslot() + timeing +"</p>" +
                    "<p> Schedule Date- " + reservationdate.toString()+ "</p>"+
                    "<p> If you have any enquiries, please contact the COVID-19 Call center: 0700 220 1122. </p>" +
                    "<b> Vaccines save lives</b>");
            Mail mail = new Mail(from, subject, to, content);

            SendGrid sg = new SendGrid(sendgridapi);
            Request mailrequest = new Request();
            try {
                mailrequest.setMethod(Method.POST);
                mailrequest.setEndpoint("mail/send");
                mailrequest.setBody(mail.build());
                Response response = sg.api(mailrequest);
                System.out.println(response.getStatusCode());
                System.out.println(response.getBody());
                System.out.println(response.getHeaders());
            } catch (IOException ex) {

            }


            // store data in session
            session.setAttribute("vackey",vackey);
            System.out.println("success");
            return  ResponseEntity.ok(new StatusMessage("success", "Great"));
        }else
        {

            System.out.println("error");
            return  ResponseEntity.status(500).body(new StatusMessage("error",errorMessage ));
        }





    }

    private LagosPaymentResponse updatePayment(UserProfile userProfile, String packag, String facid) throws URISyntaxException, JsonProcessingException {

        System.out.println("upload payment");


        System.out.println("package"+packag);
        LagosPayment lp= new LagosPayment();

        lp.setMethod("INIT_PAYMENT");
        lp.setApi_key(lagospaymentapi);
        lp.setUser("gov");
        lp.setBooking_ref_id(userProfile.getPaymentid());
        lp.setPreferred_vaccination_date(userProfile.getDate());
        lp.setSurname(userProfile.getSurname());
        lp.setOthernames(userProfile.getOthername());
        lp.setFirstname(userProfile.getFirstname());
        lp.setEmail(userProfile.getEmail());
        lp.setMobile_number(userProfile.getPhone());
        lp.setPackage_type(packag);
        lp.setFacility_id(facid);
        lp.setFacility(userProfile.getHealthfacility());
        lp.setRedirect_url("https://vaccination.gov.ng/paymentconfirmation");
        lp.setCallback_url("https://vaccination.gov.ng/paymentconfirmation");





        RestTemplate restTemplate = restTemplateBuilder.build();
        URI uri = new URI(lagosurl);





        ResponseEntity<String> result = restTemplate.postForEntity(uri, lp, String.class);



        LagosPaymentResponse data = new Gson().fromJson(result.getBody(), LagosPaymentResponse.class);


                System.out.println(result.getBody());

        Gson g = new Gson();
        LagosPaymentResponse p = g.fromJson(result.getBody(), LagosPaymentResponse.class);

        System.out.println(p.toString());


        System.out.println(p.toString());





        return p;

    }


    private  HttpHeaders getHeaders ()
    {
        String adminuserCredentials = dhisusernam+":"+dhispassword;
        String encodedCredentials =
                new String(Base64.encodeBase64(adminuserCredentials.getBytes()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return httpHeaders;
    }


    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    private void updateToDHIS(UserProfile user) throws URISyntaxException {

        webClient = WebClient.builder()
                .defaultHeaders(header -> header.setBasicAuth(dhisusernam, dhispassword))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .baseUrl(dhisurl)
                .build();
        // String url = "https://jmeter.e4eweb.space/dhis2/api/trackedEntityInstances.json?ou=s5DPBsdoE8b&program=gWuxRU2yJ1x&ouMode=CAPTURE&filter=izttywqePh2:EQ:NG-RJ89430232GV&fields=*";


        HttpHeaders httpHeaders = getHeaders();


        String url2 = dhisurl+"/dhis2/api/trackedEntityInstances/";


        // create auth credentials




        TrackedEntityInstance trackedEntityInstance= new TrackedEntityInstance();
        trackedEntityInstance.setOrgUnit(user.getOrgid());
        trackedEntityInstance.setTrackedEntityType("ag6Yk7fwUEe");

        List<Attribute> attributes= new ArrayList<>();

        Attribute surname= new Attribute();
        surname.setAttribute("aW66s2QSosT");
        surname.setValue(user.getSurname());

        Attribute firstname= new Attribute();
        firstname.setAttribute("TfdH5KvFmMy");
        firstname.setValue(user.getFirstname()+""+user.getOthername());

        Attribute vacid= new Attribute();
        vacid.setAttribute("izttywqePh2");
        vacid.setValue(user.getVac());

        Attribute gender= new Attribute();
        gender.setAttribute("CklPZdOd6H1");
        gender.setValue(user.getGender());

        Attribute email= new Attribute();
        email.setAttribute("N18AMajv6sh");
        email.setValue(user.getEmail());

        Attribute typeofid= new Attribute();
        typeofid.setAttribute("OvGXY097Hxt");
        typeofid.setValue(user.getIdtype());

        Attribute phone= new Attribute();
        phone.setAttribute("Xhzv5vjYeOW");
        phone.setValue(user.getPhone());

        Attribute dob2= new Attribute();
        dob2.setAttribute("mAWcalQYYyk");

        Attribute preg= new Attribute();
        preg.setAttribute("QQ5ZNIyAQoj");

        Attribute dobest= new Attribute();
        dobest.setAttribute("iWDekSMxycO");
        dobest.setValue(user.getKnowdob());

        Attribute address= new Attribute();
        address.setAttribute("o1BJQ0skXL5");
        address.setValue(user.getResaddress());
        System.out.println("resd add:" + user.getResaddress());




        Attribute statelgaward= new Attribute();
        statelgaward.setAttribute("rrPkYvyLP4K");
        statelgaward.setValue(user.getStateresidence()+"/"+user.getLga());



        if(user.getKnowdob().equalsIgnoreCase("false")){
            System.out.println("here");
            System.out.println("age"+ user.getEstimatedage());
            dob2.setValue(Long.toString(user.getEstimatedage()));
        }else{
            System.out.println("here2");
            System.out.println("age"+ user.getDateofbirth());
            System.out.println("dobbb"+ user.getDateofbirth());
            dob2.setValue(user.getDateofbirth());
        }

        if(user.getGender().equalsIgnoreCase("female") && user.getPregnant().equalsIgnoreCase("false") ){
            preg.setValue("notpreg");
        }else{
            preg.setValue("preg");
        }


        System.out.println("ggh"+ dob2.getValue());




        List<Enrollment> enrollments= new ArrayList<>();

        String newstring = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        Enrollment covidp= new Enrollment();
                covidp.setOrgUnit(user.getOrgid());
        covidp.setProgram("gWuxRU2yJ1x");
        covidp.setEnrollmentDate(newstring);
        covidp.setStatus("ACTIVE");
        covidp.setIncidentDate(newstring);



        enrollments.add(covidp);
        attributes.add(surname);
        attributes.add(firstname);
        attributes.add(gender);
        attributes.add(email);
        attributes.add(phone);
        attributes.add(vacid);
        attributes.add(dob2);
        attributes.add(typeofid);
        attributes.add(preg);
        attributes.add(address);
        attributes.add(statelgaward);
        //attributes.add(dobest);


        trackedEntityInstance.setAttributes(attributes);


        trackedEntityInstance.setEnrollments(enrollments);


            RestTemplate restTemplate = restTemplateBuilder.basicAuthentication(dhisusernam,dhispassword).build();
            URI uri = new URI(url2);


        Gson gson = new Gson();
        String json = gson.toJson(trackedEntityInstance);

        System.out.println(json);

            ResponseEntity<String> result = restTemplate.postForEntity(uri, trackedEntityInstance, String.class);




//            String response=  webClient.post()
//                    .uri(url2)
//                    .body(Mono.just(trackedEntityInstance), TrackedEntityInstance.class)
//                    .retrieve()
//                    .bodyToMono(String.class).block();
//
//            logRequest();
            System.out.println("response" + result.getStatusCodeValue());
            System.out.println(result.toString());
            System.out.println(result);




        // This method returns filter function which will log request data




    }

    private static ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            System.out.println("Request: {} {}"+ clientRequest.method()+ clientRequest.url());
            clientRequest.headers().forEach((name, values) -> values.forEach(value -> System.out.println("name:" + name +"\n" + "value" + value)));
            return Mono.just(clientRequest);
        });
    }


    public String generateVID() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        return "NG-"+generatedString;
    }

    private  boolean validatePhoneNumber(String phoneNo, String phoneno2, UserProfile userProfile)  {


        System.out.println(phoneNo);
        System.out.println(phoneno2);
        boolean vage= validateDateAge(userProfile);
        boolean gage= validatePhoneNumber2(phoneNo, phoneno2 );

        System.out.println("heyy"+ vage);
        if(phoneNo.startsWith("080") || phoneNo.startsWith("070") || phoneNo.startsWith("090") || phoneNo.startsWith("081") || phoneNo.startsWith("071")){
            if(vage && gage){
                return true;
            }else
            {
                return false;
            }

        }else{
            errorMessage="Please check your phone number. ex. 07002201122";
            return false;
        }


    }

    private boolean validatePhoneNumber2(String phoneNo, String phoneno2) {
        if(phoneNo.equalsIgnoreCase(phoneno2)){
            if(phoneNo.length()==11){
                return true;
            }


        }else{
            errorMessage="Phone number does not match!";
            return false;
        }
        errorMessage="Invalid phone number";
        return false;
    }

    public boolean validateDateAge(UserProfile userp){


        if(userp.getKnowdob().equalsIgnoreCase("false")){
            System.out.println("here");
            System.out.println("age"+ userp.getEstimatedage());

            if(userp.getEstimatedage()<18||userp.getEstimatedage()==0){

                errorMessage="Input your Estimated Age (Approved age is 18 or more)";
                return false;
            }


        }else{

            if(userp.getDateofbirth()==""){
                errorMessage="Input your Date of birth";
                return false;
            }

        }
        return true;

    }








    @RequestMapping( value = "/reservations" )
   public String getReservations(@RequestParam(value="date", required=false)String dateString, Model model) {

        // Use model to transfer the model of data to the view.
        model.addAttribute("roomReservations",this.roomReservationService.getRoomReservationsForDate(dateString));

        if(dateString==null){
            Date date = new Date();
            dateString= new SimpleDateFormat("yyyy-MM-dd").format(date);
        }


        model.addAttribute("dateselected", dateString);
        return "admin/reservations-view";	// Pass the string to the Spring Boot template for displaying the view
    }




    private static final Random RND = new Random(System.currentTimeMillis());
    public static String generate(CodeConfig config) {
        StringBuilder sb = new StringBuilder();
        char[] chars = config.getCharset().toCharArray();
        char[] pattern = config.getPattern().toCharArray();

        if (config.getPrefix() != null) {
            sb.append(config.getPrefix());
        }

        for (int i = 0; i < pattern.length; i++) {
            if (pattern[i] == CodeConfig.PATTERN_PLACEHOLDER) {
                sb.append(chars[RND.nextInt(chars.length)]);
            } else {
                sb.append(pattern[i]);
            }
        }

        if (config.getPostfix() != null) {
            sb.append(config.getPostfix());
        }

        return sb.toString();
    }

    public String generator(){
        CodeConfig config2 = CodeConfig.length(2).withCharset(CodeConfig.Charset.ALPHABETIC);
        String postfi=generate(config2);

        CodeConfig config3 = CodeConfig.length(2).withCharset(CodeConfig.Charset.ALPHABETIC);
        String postfiat=generate(config3);

        CodeConfig config = CodeConfig.length(8).withPrefix("NG-"+postfiat).withCharset(CodeConfig.Charset.NUMBERS).withPostfix(postfi);

        // when
        String code = generate(config);
        return code;
    }


    public void sendemail(String email){

//            ScheduleEmailRequest welcomemail= new ScheduleEmailRequest();
//            welcomemail.setEmail(userProfile.getEmail());
//            welcomemail.setSubject("NPHCDA- Vaccination Booking Confirmed");
//            welcomemail.setName(userProfile.getSurname()+" " +userProfile.getFirstname());
//            welcomemail.setBody(""+
//                    "<p> Vaccination ID -"+ userProfile.getVac() + "</p>"+
//                    "<p> Health Facility -"+ healthCenterRepo.findByHealthCenterId(user.getFacilityid()).getHealthCenter() + "</p>"+
//                    "<p> Schedule Session - "+ myRoom.getTimeslot() +"</p>"+
//                    "<p> Schedule Date- " + reservationdate.toString()+ "</p>" +
//                    "");
//
//
//            welcomemail.setTemplate("confirmation.html");

        // emailservice.scheduleEmail(welcomemail);


    }


}
