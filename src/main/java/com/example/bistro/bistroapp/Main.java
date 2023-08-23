package com.example.bistro.bistroapp;

import com.example.bistro.bistroapp.console.BistroMenu;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Main.class, args);
        BistroMenu bistroM = context.getBean(BistroMenu.class);
        bistroM.runConsole();
    }
}
