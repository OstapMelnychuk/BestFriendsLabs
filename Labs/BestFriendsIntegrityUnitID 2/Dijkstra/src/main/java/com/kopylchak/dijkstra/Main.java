package com.kopylchak.dijkstra;

import com.google.inject.Guice;
import com.kopylchak.dijkstra.controller.Controller;
import com.kopylchak.dijkstra.ioc.AppInjector;

public class Main {
    public static void main(String[] args) {
        Guice.createInjector(new AppInjector()).getInstance(Controller.class).start();
    }
}