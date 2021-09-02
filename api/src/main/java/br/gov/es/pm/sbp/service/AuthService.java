package br.gov.es.pm.sbp.service;

import br.gov.es.pm.sbp.config.ApplicationProperties;
import br.gov.es.pm.sbp.domain.User;
import br.gov.es.pm.sbp.repository.CargoRepository;
import br.gov.es.pm.sbp.repository.QuadroRepository;
import br.gov.es.pm.sbp.repository.UserRepository;
import br.gov.es.pm.sbp.security.AuthoritiesConstants;
import br.gov.es.pm.sbp.service.dto.UserDTO;
import br.gov.es.pm.sbp.web.rest.vm.LoginVM;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Transactional
public class AuthService {
    private final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final UserService userService;
    private final ApplicationProperties applicationProperties;

    private final CargoRepository cargoRepository;
    private final QuadroRepository quadroRepository;

    public AuthService(
        UserRepository userRepository,
        UserService userService,
        ApplicationProperties applicationProperties,
        CargoRepository cargoRepository,
        QuadroRepository quadroRepository) {
        this.userRepository = userRepository;
        this.applicationProperties = applicationProperties;
        this.userService = userService;
        this.cargoRepository = cargoRepository;
        this.quadroRepository = quadroRepository;
    }

    public LoginVM processToken(String token) throws BadCredentialsException {
        Claims claims;
        try {
            claims = Jwts.parser()
                .setSigningKey(applicationProperties.getChaveToken())
                .parseClaimsJws(token)
                .getBody();
        } catch (ExpiredJwtException e) {
            throw new BadCredentialsException("Token de acesso expirado.");
        } catch (Exception e) {
            log.info("Token inválido - {}", e.getMessage());
            throw new BadCredentialsException("Token inválido.");
        }

        registerOrUpdateUser(claims);
        LoginVM loginVM = new LoginVM();
        loginVM.setToken(token);
        loginVM.setUsername(claims.getSubject());
        loginVM.setPassword(loginVM.getUsername());
        loginVM.setRememberMe(true);

        return loginVM;
    }

    private boolean registerOrUpdateUser(Claims claims) {
        Optional<User> optionalUser = userRepository.findOneByLogin(claims.getSubject());
        if (optionalUser.isPresent()) {
            UserDTO userDTO = setFieldsUser(new UserDTO(optionalUser.get()), claims);
            userService.updateUser(userDTO);
            return false;
        }

        UserDTO userDTO = setFieldsUser(new UserDTO(), claims);
        userService.createUser(userDTO);

        return true;
    }

    private UserDTO setFieldsUser(UserDTO userDTO, Claims claims) {
        Map<String, Object> usuario = (HashMap<String, Object>) claims.get("usuario");

        Long cargoId = Long.valueOf(usuario.get("cargoId").toString());
        Long quadroId = Long.valueOf(usuario.get("quadroId").toString());

        if (userDTO.getCargo() == null || cargoId != userDTO.getCargo().getId()) {
            cargoRepository.findById(cargoId).ifPresent(cargo -> userDTO.setCargo(cargo));
        }

        if (userDTO.getQuadro() == null || quadroId != userDTO.getQuadro().getId()) {
            quadroRepository.findById(quadroId).ifPresent(quadro -> userDTO.setQuadro(quadro));
        }

        if (userDTO.getLogin() == null) {
            userDTO.setLogin(claims.getSubject());
            Set<String> authorities = new HashSet<>();
            authorities.add(AuthoritiesConstants.USER);
            userDTO.setAuthorities(authorities);
            userDTO.setActivated(true);
        }

        userDTO.setNome(usuario.get("nome").toString());
        userDTO.setRg((Integer) usuario.get("rg"));
        userDTO.setNumeroFuncional((Integer) usuario.get("numeroFuncional"));
        userDTO.setEmail(usuario.get("email").toString());

        return userDTO;
    }
}
