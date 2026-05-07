package ca.vetClinic.infra.security;

import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.infra.mapper.UserPrincipalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
	private final AccountRepository accountRepository;
	private final UserPrincipalMapper mapper;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return mapper.fromAccount(accountRepository.findByEmail(email));
	}
}
