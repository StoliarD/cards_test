package ru.cards_mobile.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.cards_mobile.test.core.AccountsService;
import ru.cards_mobile.test.entities.Account;
import ru.cards_mobile.test.entities.Response;

import java.util.Date;

@RestController
public class Controller {

    private final String CREATED = "entity created successfully";

    @Autowired
    private AccountsService service;

    @RequestMapping("/create")
    public Response create(@RequestParam(name = "snils") String snils,
                           @RequestParam(name = "fio") String fio,
                           @RequestParam(name = "birth") @DateTimeFormat(iso = ISO.DATE) Date birthStr) {
        boolean put = service.put(new Account(snils, fio, birthStr));
        return put ? Response.ok("created successfully") : Response.error("snils is already mapped");
    }

    @RequestMapping("/find")
    public Response find(@RequestParam(name = "snils") String snils) {
        return Response.ok(service.find(snils));
    }

    @RequestMapping("/update")
    public Response update(@RequestParam(name = "snils") String snils,
                           @RequestParam(name = "fio") String fio,
                           @RequestParam(name = "birth") @DateTimeFormat(iso = ISO.DATE) Date birthStr) {
        boolean replaced = service.replaceBySnils(new Account(snils, fio, birthStr));
        return replaced ?
                Response.ok("account successfully updated") :
                Response.error("no account mapped for that snils found or no update occurred");
    }







}
