package ca.vetClinic.infra.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {

	private final JwtValidator jwtValidator;
	private final JwtExtractor jwtExtractor;
	private final UserDetailsService userDetailsService;

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		String path = request.getServletPath();
		return path.startsWith("/auth/") || path.startsWith("/v3/api-docs") || path.startsWith("/swagger-ui")
				|| path.startsWith("/actuator");
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		log.debug("Processing JWT filter for: {}", request.getServletPath());

		String authHeader = request.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			log.debug("No Bearer token found, continuing filter chain");
			filterChain.doFilter(request, response);
			return;
		}

		String token = authHeader.substring(7);

		if (!jwtValidator.isValid(token)) {
			log.warn("Invalid JWT token");
			filterChain.doFilter(request, response);
			return;
		}

		String username = jwtExtractor.extractUsername(token);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			log.debug("Authenticated user: {}", username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
					null, userDetails.getAuthorities());

			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
}