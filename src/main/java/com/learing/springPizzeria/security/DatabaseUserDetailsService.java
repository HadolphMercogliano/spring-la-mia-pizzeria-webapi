package com.learing.springPizzeria.security;

import com.learing.springPizzeria.model.User;
import com.learing.springPizzeria.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DatabaseUserDetailsService implements UserDetailsService {
  
  @Autowired
  UserRepo userRepo;
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> result = userRepo.findByEmail(username);
    if (result.isPresent()) {
      return new DatabaseUserDetails(result.get());
    } else {
      throw new UsernameNotFoundException("Utente " + username + " non trovato");
    }
  }
}
