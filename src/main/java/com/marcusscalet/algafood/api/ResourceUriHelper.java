package com.marcusscalet.algafood.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

	public static void addUriInReponseHeader(Object resourceId) {

		// Cria uma uri a partir do request atual (pengando protocolo, port, host,
		// context...)
		// e adiciona junto ao path o /{id} do objeto criado
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(resourceId)
				.toUri();

		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();

		response.setHeader(HttpHeaders.LOCATION, uri.toString());

	}
}
