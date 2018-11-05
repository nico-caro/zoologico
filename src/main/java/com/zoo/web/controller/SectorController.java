package com.zoo.web.controller;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import com.zoo.web.DAO.SectorDAO;

import com.zoo.web.entity.Sector;

@Controller
@RequestMapping("/sector")
public class SectorController {

	@Autowired
	private SectorDAO sDAO;
	
	@GetMapping("/listar")
	public String listar(Model model)
	{
		model.addAttribute("sectores", sDAO.crud().findAll());
		
		return "listars.html";
	}
	
	@GetMapping("/eliminar")
	public String eliminar(Model model,
			RedirectAttributes ra,
			@RequestParam("id")int id)
	{
		
		String mensaje="";
		
		try
		{
			sDAO.crud().deleteById(id);
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
		
		return "agregars.html";
	}
	
	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra,
			@RequestParam("txtNombre")String nombre,
			@RequestParam("txtDescripcion")String descripcion)
	{
		String mensaje ="Error al agregar";
		
		Sector sector = new Sector();
		sector.setNombre(nombre);
		sector.setDescripcion(descripcion);
		
		Sector s = sDAO.crud().save(sector);
		
		if(s!=null)
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
    	Sector s=null;
    	try 
    	{
    		s = sDAO.crud().findById(id).get();
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El sector no existe");
			return "redirect:listar";
		}
        
    	model.addAttribute("s", s);
		
    	return "modificars.html";
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
			Sector sector = sDAO.crud().findById(id).get();
			sector.setNombre(nombre);
			sector.setDescripcion(descripcion);
			Sector s = sDAO.crud().save(sector);
			
			if(s!=null)
			{
				mensaje= " Modificado correctamente";
			}
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El sector no existe");
			return "redirect:listar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listar";
	}
}
