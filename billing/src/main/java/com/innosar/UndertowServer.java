package com.innosar;

import static io.undertow.servlet.Servlets.defaultContainer;
import static io.undertow.servlet.Servlets.deployment;
import static io.undertow.servlet.Servlets.errorPage;
import static io.undertow.servlet.Servlets.filter;
import static io.undertow.servlet.Servlets.listener;
import static io.undertow.servlet.Servlets.servlet;

import java.net.URL;

import javax.faces.webapp.FacesServlet;
import javax.servlet.ServletException;

import org.h2.server.web.DbStarter;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.filter.DelegatingFilterProxy;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

public class UndertowServer {

	private static final String MYAPP = "/myapp";

	public static void main(String[] args) {


		try {

			URL springConfig = UndertowServer.class.getClassLoader().getResource("WEB-INF/applicationContext-Security.xml");
			URL logConfig = UndertowServer.class.getClassLoader().getResource("log4j.properties");

			DeploymentInfo servletBuilder = deployment()
					.setClassLoader(UndertowServer.class.getClassLoader())
					.setContextPath(MYAPP)
					.setDeploymentName("billing.war")
					.addInitParameter("contextConfigLocation", "classpath:WEB-INF/applicationContext-Security.xml")
					.addInitParameter("javax.faces.FACELETS_VIEW_MAPPINGS", "*.xhtml")
					.addInitParameter("facelets.DEVELOPMENT", "false")
					.addInitParameter("javax.faces.STATE_SAVING_METHOD", "server")
					.addInitParameter("javax.faces.DEFAULT_SUFFIX", "*.xhtml")
					.addInitParameter("primefaces.THEME", "start")
					.addInitParameter("log4jConfigLocation", "classpath:log4j.properties")
					.addServlets(
							servlet("FacesServlet", FacesServlet.class)
									.setLoadOnStartup(1)
									.addMapping("*.xhtml"))
					.addListeners(
							listener(ContextLoaderListener.class),
							listener(HttpSessionEventPublisher.class),
							listener(DbStarter.class))
					.addFilters(
							filter("fileUpload", FileUploadFilter.class),
							filter("springSecurity", DelegatingFilterProxy.class))
					.addWelcomePage("login.xhtml")
					.addErrorPages(
							errorPage("/404.xhtml", 404),
							errorPage("/500.xhtml", 500)
					)
					;

			DeploymentManager manager = defaultContainer().addDeployment(servletBuilder);
			manager.deploy();

			HttpHandler servletHandler = manager.start();
			PathHandler path = Handlers.path(Handlers.redirect(MYAPP))
					.addPrefixPath(MYAPP, servletHandler);
			Undertow server = Undertow.builder()
					.addHttpListener(8080, "localhost")
					.setHandler(path)
					.build();
			server.start();
		} catch (ServletException e) {
			throw new RuntimeException(e);
		}
	}
}
