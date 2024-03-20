package com.nisum.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nisum.components.JWTComponent;
import com.nisum.entities.Phone;
import com.nisum.entities.User;
import com.nisum.exception.NisumException;
import com.nisum.repositories.PhoneRepository;
import com.nisum.repositories.UserRepository;
import com.nisum.services.UserService;
import com.nisum.utils.Constantes;
import com.nisum.utils.Utilidades;
import com.nisums.dto.MensajeDTO;
import com.nisums.dto.PhoneDTO;
import com.nisums.dto.UserDTO;
/**
 * Clase que implementa las funciones de user service
 * @author Cristian Altamirano LLanos
 */
@Service
public class UserServiceImpl implements UserService{
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);	

	@Autowired
	private UserRepository ur;
	@Autowired
	private JWTComponent jwtc;
	@Autowired
	private PhoneRepository pr;
	@Override
	public MensajeDTO save(UserDTO userDTO) {
		try {
			MensajeDTO mdto = validarDatosFrontUsuario(userDTO);
			if (mdto.getEstado().equalsIgnoreCase(Constantes.RESPUESTA_REST_FAIL)) {
				return mdto;
			}
			User userOld = ur.getByEmail(userDTO.getEmail());
			if (userOld != null) {
				return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "El correo ya registrado");
			}
			User user = converToEntity(userDTO);
			if (user == null) {
				throw new NisumException("No se puede crear el usuario");
			}
			user.setTokenJWT(jwtc.generateTokenOnCreateUser(user.getId()));
			user.setCreationDate(Utilidades.getCurrenDate());
			user.setModificationDate(user.getCreationDate());
			user.setActivo(true);
			ur.save(user);
			mdto.setTokenJWT(user.getTokenJWT());
			return mdto;
		}catch(Exception e) {
			logger.error("No se puede almacenar usuario",e);
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "Problemas al crear usuario");
		}
	}
	
	@Override
	public MensajeDTO update(UserDTO userDTO) {
		MensajeDTO mdto = validarDatosFrontUsuario(userDTO);
		if (mdto.getEstado().equalsIgnoreCase(Constantes.RESPUESTA_REST_FAIL)) {
			return mdto;
		}
		User userOld = ur.getByEmail(userDTO.getEmail());
		if (userOld == null) {
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "Usuario no existe");
		}
		User user = converToEntity(userDTO);
		Boolean activo = userOld.isActivo();
		if (userDTO.isActivo() != null) {
			activo = userDTO.isActivo();
		}
		if (user != null) {
			user.setId(userOld.getId());
			pr.deletePhoneByIdUser(userOld.getId());
			user.setCreationDate(userOld.getCreationDate());
			user.setModificationDate(Utilidades.getCurrenDate());
			user.setTokenJWT(userOld.getTokenJWT());
			user.setActivo(activo);
			ur.save(user);
			mdto.setTokenJWT(userOld.getTokenJWT());
			return mdto;
		}else {
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "No se pudo modificar el usuario");
		}
		
	}
	
	@Override
	public UserDTO getByEmail(String email) {
		try {
			User user = ur.getByEmail(email);
			UserDTO udto = converToDTO(user);
			if (udto != null) {
				udto.setPassword("");
			}
			return udto;
		}catch(Exception e) {
			logger.error("No se pudo encontrar datos del usuario por email", e);
			return null;
		}
	}
	
	@Override
	public MensajeDTO delete(UserDTO userDTO) {
		try {
		User userOld = ur.getByEmail(userDTO.getEmail());
		if (userOld == null) {
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "No existe el email del usuario");
		}
		ur.delete(userOld);
		return new MensajeDTO(Constantes.RESPUESTA_REST_OK, "Usuario eliminado con exito");
		}catch(Exception e) {
			logger.error("No se puede eliminar el usuario", e);
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "No se pudo eliminar el usuario");
		}
	}
	
	@Override
	public List<UserDTO> getAll(){
		List<UserDTO> lud = new ArrayList<>();
		try {
			List<User> lu = ur.findAll();
			for (int i=0; i < lu.size(); i++) {
				UserDTO ud = converToDTO(lu.get(i));
				lud.add(ud);
			}
		}catch(Exception e) {
			logger.error("No se puede obtener todos los usuarios", e);
		}
		return lud;
	}



	/**
	 * 
	 * @param udto
	 * @return
	 */
	private MensajeDTO validarDatosFrontUsuario(UserDTO udto) {
		try {
			if (Utilidades.validarEmail(udto.getEmail()).compareTo(false) == 0) {
				return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "Email invalido");
			}
			if (Utilidades.validarContrasena(udto.getPassword()).compareTo(false) == 0) {
				return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "Contraseña invalida");
			}
			return new MensajeDTO(Constantes.RESPUESTA_REST_OK, "OK");
		}catch(Exception e) {
			return new MensajeDTO(Constantes.RESPUESTA_REST_FAIL, "Email invalido");
		}
		
	}
	/**
	 * 
	 * @param user
	 * @return
	 */
	private UserDTO converToDTO(User user) {
		try {
			if (user == null) {
				throw new NisumException("No existe el usuario");
			}
			UserDTO udto = new UserDTO();
			udto.setCreationDate(Utilidades.tsToFechaDDMMAAA(user.getCreationDate()));
			udto.setEmail(user.getEmail());
			udto.setModificationDate(Utilidades.tsToFechaDDMMAAA(user.getModificationDate()));
			udto.setName(user.getName());
			udto.setPassword(user.getPassword());
			udto.setId(user.getId());
			udto.setTokenJWT(user.getTokenJWT());
			udto.setActivo(user.isActivo());
			List<PhoneDTO> lpdto = new ArrayList<>();
			for (int i = 0; i < user.getPhones().size(); i++) {
				lpdto.add(converToPhoneDTO(user.getPhones().get(i)));
			}
			udto.setPhones(lpdto);
			return udto;
		}catch(Exception e) {
			logger.error("No se puede transformar la entity a DTO", e);
			return null;
		}
	}
	
	
	/**
	 * 
	 * @param udto
	 * @return
	 */
	private User converToEntity(UserDTO udto) {
		try {
			User u = new User();
			u.setEmail(udto.getEmail());
			u.setName(udto.getName());
			u.setPassword(udto.getPassword());
			if (udto.getId() == null) {
				u.setId(Utilidades.generateUniqueId());
			}else {
				u.setId(udto.getId());
			}
			List<Phone> lp = new ArrayList<>();
			for (int i = 0; i < udto.getPhones().size(); i++) {
				lp.add(converToPhone(udto.getPhones().get(i), u));
			}
			u.setPhones(lp);
			return u;
		}catch(Exception e) {
			logger.error("No se puede transformar desde dto a entity",e);
			return null;
		}
		
	}
	/**
	 * 
	 * @param phone
	 * @param udto
	 * @return
	 */
	private PhoneDTO converToPhoneDTO(Phone phone) {
		PhoneDTO pdto = new PhoneDTO();
		pdto.setCitycode(phone.getCityCode());
		pdto.setCountrycode(phone.getCountryCode());
		pdto.setId(phone.getId());
		pdto.setNumber(phone.getNumber());
		return pdto;
	}
	/**
	 * 
	 * @param pdto
	 * @param user
	 * @return
	 */
	private Phone converToPhone(PhoneDTO pdto, User user) {
		Phone p = new Phone();
		p.setCityCode(pdto.getCitycode());
		p.setCountryCode(pdto.getCountrycode());
		p.setId(pdto.getId());
		p.setNumber(pdto.getNumber());
		p.setUser(user);
		return p;
	}

	
	
}
