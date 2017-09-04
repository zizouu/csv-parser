package com.zizou.csvparser.service;

import org.springframework.context.ApplicationContext;

/**
 * Created by zizou on 2017-09-04.
 */
public class ModuleService {
    private static ApplicationContext context;

    public static Object getBean(String name){
        return context.getBean(name);
    }

    public static <T> T getBean(Class<T> type){
        return context.getBean(type);
    }

    public static ApplicationContext getContext(){
        return context;
    }

    public static void setContext(ApplicationContext context){
        ModuleService.context = context;
    }
}
