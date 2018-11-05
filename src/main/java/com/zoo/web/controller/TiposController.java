package com.zoo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.zoo.web.DAO.TipoDAO;
import com.zoo.web.entity.Tipo;

@Controller
@RequestMapping("/tipo")
public class TiposController {
	@Autowired
	private TipoDAO tDAO;
	
	@GetMapping("/listar")
	public String listar(Model model)
	{
		model.addAttribute("tipos", tDAO.crud().findAll());
		return "listart.html";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(Model model,
			RedirectAttributes ra,
			@RequestParam("id")int id)
	{
		
		String mensaje="";
		
		try
		{
			tDAO.crud().deleteById(id);
			mensaje= "Eliminado correctamente";
		}
		catch (Exception ex) 
		{
			mensaje="Error al eliminar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		return "redirect:listar";
	}
	
	@GetMapping("/crear")
	public String crear(Model model)
	{
		
		return "agregart.html";
	}
	
	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra,
			@RequestParam("txtNombre")String nombre,
			@RequestParam("txtDescripcion")String descripcion)
	{
		String mensaje ="Error al agregar";
		
		Tipo tipo = new Tipo();
		tipo.setNombre(nombre);
		tipo.setDescripcion(descripcion);
		
		Tipo t = tDAO.crud().save(tipo);
		
		if(t!=null)
		{
			mensaje= " Agregado correctamente";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:crear";
	}
	 
    @GetMapping("/editar")
    public String editar(Model model,
    		RedirectAttributes ra,
    		@RequestParam("id") int id)
    {
    	Tipo t=null;
    	try 
    	{
    		t = tDAO.crud().findById(id).get();
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El tipo no existe");
			return "redirect:listar";
		}
        
    	model.addAttribute("t", t);
		
    	return "modificart.html";
    }
    
    @PostMapping("/actualizar")
	public String actualizar(Model model, RedirectAttributes ra,
			@RequestParam("txtId") int id,
			@RequestParam("txtNombre")String nombre,
			@RequestParam("txtDescripcion")String descripcion)
	{
		String mensaje ="Error al modificar";
		
		try 
    	{
			Tipo tipo = tDAO.crud().findById(id).get();
			tipo.setNombre(nombre);
			tipo.setDescripcion(descripcion);
			Tipo t = tDAO.crud().save(tipo);
			
			if(t!=null)
			{
				mensaje= " Modificado correctamente";
			}
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El tipo no existe");
			return "redirect:listar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listar";
	}
}
