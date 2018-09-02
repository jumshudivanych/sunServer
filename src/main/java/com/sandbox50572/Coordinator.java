package com.sandbox50572;

public class Coordinator implements Runnable {

    //конструктор
    public Coordinator() {

    }

    @Override
    public void run() {

        //создание объекта simpleHttpServer1
        Runnable simpleHttpServer1 = (Runnable) new SimpleHttpServer();
        //создание потока
        Thread threadHttpServer = new Thread(simpleHttpServer1);
        //стартуем поток сервера
        threadHttpServer.start();

    }
}
