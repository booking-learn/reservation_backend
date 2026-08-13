package ca.vetClinic.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class PasswordChangeRequiredFilter extends OncePerRequestFilter {

	private static final List<String> ALLOWED_PATHS = List.of("/employee/password");

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal userPrincipal) {
			boolean pathIsAllowed = ALLOWED_PATHS.contains(request.getRequestURI());

			if (userPrincipal.isMustChangePassword() && !pathIsAllowed) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You must change your password first");
				return;
			}
		}

		filterChain.doFilter(request, response);
	}
}