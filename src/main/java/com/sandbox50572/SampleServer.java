package com.sandbox50572;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Thread.NORM_PRIORITY;

/**
 * TCP/IP server
 */

public class SampleServer implements Runnable {

    Socket s;
    int num;

    //конструктор
    public SampleServer(int num, Socket s)
    {
        // копируем данные
        this.num = num;
        this.s = s;

        //TODO !!! и запускаем новый вычислительный поток (см. ф-ю run())
        //setDaemon(true);
        //setPriority(NORM_PRIORITY);
        //start();
    }

    @Override
    public void run() {

        try
        {
            int i = 0; // счётчик подключений

            // привинтить сокет на локалхост, порт 3128
            ServerSocket server = new ServerSocket(3128, 0,
                    InetAddress.getByName("localhost"));

            System.out.println("server is started");

            // слушаем порт
            while(true)
            {
                // ждём нового подключения, после чего запускаем обработку клиента
                // в новый вычислительный поток и увеличиваем счётчик на единичку
                new SampleServer(i, server.accept());
                i++;
            }
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений

        try
        {
            // из сокета клиента берём поток входящих данных
            InputStream is = s.getInputStream();
            // и оттуда же - поток данных от сервера к клиенту
            OutputStream os = s.getOutputStream();

            // буффер данных в 64 килобайта
            byte buf[] = new byte[64*1024];
            // читаем 64кб от клиента, результат - кол-во реально принятых данных
            int r = is.read(buf);

            // создаём строку, содержащую полученную от клиента информацию
            String data = new String(buf, 0, r);

            // добавляем данные об адресе сокета:
            data = ""+num+": "+"\n"+data;

            // выводим данные:
            os.write(data.getBytes());

            // завершаем соединение
            s.close();
        }
        catch(Exception e)
        {System.out.println("init error: "+e);} // вывод исключений

    }

}
