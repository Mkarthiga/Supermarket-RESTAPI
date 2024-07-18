package com.supermarkets.ProxyFactory;

import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import com.supermarkets.v1.handler.CrudHandler;

public class ProxyFactoryClass {

	private static final String LATEST_VERSION = "V1";

	public static String currentVersion;

	public static Class<?> clazz;

	public static CrudHandler createProxy(ApplicationContext context, String version, String handler)
			throws BeansException {
		String handlerName = handler+"Handler"+ version.toUpperCase();
		try {
			ProxyFactory factory = new ProxyFactory();
			factory.setTarget(context.getBean(handlerName));
			currentVersion=version;
			clazz = Class.forName("com.supermarkets."+currentVersion+".model."+handler.toUpperCase().charAt(0)+handler.substring(1));
			return (CrudHandler) factory.getProxy();
		} catch (BeansException | ClassNotFoundException e) {

			try {
				ProxyFactory factory = new ProxyFactory();
				factory.setTarget(context.getBean(handler+"Handler"+LATEST_VERSION));
				currentVersion=LATEST_VERSION.toLowerCase();
				clazz = Class.forName("com.supermarkets."+currentVersion+".model."+handler.toUpperCase().charAt(0)+handler.substring(1));
				return (CrudHandler) factory.getProxy();
			} catch (BeansException | ClassNotFoundException ex) {
				throw new RuntimeException("Default handler class not found", ex);
			}

		}
	}

}