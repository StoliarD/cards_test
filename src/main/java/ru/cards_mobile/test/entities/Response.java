package ru.cards_mobile.test.entities;


public class Response {
//    private static final String OK = "OK";
//    private static final String ERROR = "ERROR";

    private final Status status;
    private final Object info;

    private Response(Status status, Object info) {
        this.status = status;
        this.info = info;
    }

    public static Response error(String message) {
        return new Response(Status.ERROR, message);
    }

    public static Response ok(String message) {
        return new Response(Status.OK, message);
    }

    public static Response ok(Account obj) {
        return new Response(Status.OK, obj);
    }

    public Status getStatus() {
        return status;
    }

    public Object getInfo() {
        return info;
    }

    enum Status {OK, ERROR}

}
