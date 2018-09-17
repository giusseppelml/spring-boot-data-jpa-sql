package com.curso.java.spring.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.java.spring.app.models.entity.Cliente;
import com.curso.java.spring.app.service.IClienteService;
import com.curso.java.spring.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente") // lo use para el editar en este ejercicio
public class ClienteController {
	
	/* @Qualifier("clienteDaoJPA") en caso de aver otro igual usar esta anotacion para distinguir
	 el nombre que esta en el @Qualifier("clienteDaoJPA") tiene que ser el mismo que en el repository
	 se coloca abajo del @Autowired*/
	
	//titulos
	@Value("${application.controller.cliente}")
	private String clienteLabel;
	
	@Value("${application.controller.formulario}")
	private String formularioLabel;
	
	@Value("${application.controller.editar}")
	private String editarCliente;
	
	//mensajes flash
	@Value("${application.controller.registrado}")
	private String registrado;
	
	@Value("${application.controller.editado}")
	private String editado;
	
	@Value("${application.controller.eliminado}")
	private String eliminado;
	
	@Value("${application.controller.errorcero}")
	private String errorCero;
	
	@Value("${application.controller.errornodatabase}")
	private String noDataBase;
	
	//cabeceras de atributos de clientes
	//form
	@Value("${application.controller.nombref}")
	private String nombreForm;
	
	@Value("${application.controller.apellidof}")
	private String apellidosForm;
	
	@Value("${application.controller.mailf}")
	private String mailForm;
	
	@Value("${application.controller.fechaf}")
	private String fechaForm;
	
	//listar cabeceras de atrbutos Clientes y botones
	@Value("${application.controller.idl}")
	private String idListar;
	
	@Value("${application.controller.nombrel}")
	private String nombreListar;
	
	@Value("${application.controller.apellidol}")
	private String apellidosListar;
	
	@Value("${application.controller.maill}")
	private String mailListar;
	
	@Value("${application.controller.fechal}")
	private String fechaListar;

	@Value("${application.controller.crearl}")
	private String crearListar;
	
	@Value("${application.controller.modificarl}")
	private String modificarListar;
	
	@Value("${application.controller.eliminarl}")
	private String eliminarListar;
	
	@Autowired
	private IClienteService clienteService;
	
	@RequestMapping(value="/listar", method=RequestMethod.GET) //url de nuestra vista
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		//Paginacion de datos
		Pageable pageableRequest = new PageRequest(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageableRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/listar", clientes);
		
		model.addAttribute("titulo", clienteLabel);
		model.addAttribute("clientelist", clientes);
		model.addAttribute("page", pageRender);
		
		model.addAttribute("id", idListar);
		model.addAttribute("nombre", nombreListar);
		model.addAttribute("apellidos", apellidosListar);
		model.addAttribute("mail", mailListar);
		model.addAttribute("fecha", fechaListar);
		model.addAttribute("crear", crearListar);
		model.addAttribute("modificar", modificarListar);
		model.addAttribute("eliminar", eliminarListar);
		
		return "listar"; //el nombre de nuestra vista
	}
	
	@RequestMapping(value="/form") //url de nuestra vista crear
	public String createCliente(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", formularioLabel);
		model.put("nombre", nombreForm);
		model.put("apellido", apellidosForm);
		model.put("mail", mailForm);
		model.put("fecha", fechaForm);
		return "form"; //el nombre de nuestra vista
	}
	
	@RequestMapping(value="/form/{id}") //url de nuestra vista modificar
	public String updateCliente(@PathVariable(value="id") long id, Map<String, Object> model,
			RedirectAttributes flash) {
		Cliente cliente = null;
		if(id > 0) {
			cliente = clienteService.findOne(id);
			if(cliente == null) {
				flash.addFlashAttribute("error", noDataBase);
				return "redirect:/listar";
			}
		}else {
			flash.addFlashAttribute("error", errorCero);
			return "redirect:/listar";
		}
		
		model.put("cliente", cliente);
		model.put("titulo", editarCliente);
		model.put("nombre", nombreForm);
		model.put("apellido", apellidosForm);
		model.put("mail", mailForm);
		model.put("fecha", fechaForm);
		
		return "form";
	}
	
	@RequestMapping(value="/form", method=RequestMethod.POST) //url de nuestra vista
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			RedirectAttributes flash, SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute("titulo", formularioLabel);
			return "form";
		}
		String mensajeFlash = (cliente.getId() > 0) ? editado: registrado;
		
		clienteService.save(cliente);
		status.setComplete(); // con esta linea elimino mi variable de session
		flash.addFlashAttribute("success", mensajeFlash);
		return "redirect:listar"; //el nombre de nuestra vista
	}
	
	@RequestMapping(value="/eliminar/{id}")
	public String eliminar(@PathVariable(value="id") long id, RedirectAttributes flash) {
		
		if(id > 0) {
			clienteService.delete(id);
			flash.addFlashAttribute("success", eliminado);
		}
		
		return "redirect:/listar";
	}
}
