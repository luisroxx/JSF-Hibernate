package br.com.zerto.website.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FiltroSessao implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpSession session = ((HttpServletRequest) req).getSession(false);

		if (session.getAttribute("idUsuarioLogado") != null) {
			chain.doFilter(request, res);
		} else {
			HttpServletResponse response = (HttpServletResponse) res;

			String loginURL = request.getContextPath() + "/login.xhtml";
			response.sendRedirect(loginURL);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
