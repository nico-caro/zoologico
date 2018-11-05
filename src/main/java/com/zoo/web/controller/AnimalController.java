package com.zoo.web.controller;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zoo.web.DAO.AnimalDAO;
import com.zoo.web.DAO.SectorDAO;
import com.zoo.web.DAO.TipoDAO;
import com.zoo.web.entity.Animal;
import com.zoo.web.entity.Sector;
import com.zoo.web.entity.Tipo;

@Controller
@RequestMapping("/animal")
public class AnimalController {

	@Autowired
	private AnimalDAO aDAO;
	
	@Autowired
	private SectorDAO sDAO;
	
	@Autowired 
	private TipoDAO tDAO;
	
	@GetMapping("/listar")
	public String listar(Model model)
	{
		model.addAttribute("animales", aDAO.crud().findAll());
		return "listar.html";
	}
	@GetMapping("/eliminar")
	public String eliminar(Model model,
			RedirectAttributes ra,
			@RequestParam("id")int id)
	{
		
		String mensaje="";
		
		try
		{
			aDAO.crud().deleteById(id);
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
		model.addAttribute("tipos", tDAO.crud().findAll());
		model.addAttribute("sectores", sDAO.crud().findAll());
		
		return "agregar.html";
	}
	
	@PostMapping("/almacenar")
	public String almacenar(Model model, RedirectAttributes ra,
			@RequestParam("txtNombre")String nombre,
			@RequestParam("txtPeso")int peso,
			@RequestParam("cboTipo") int tipoId,
			@RequestParam("txtGenero")String genero,
			@RequestParam("txtFechaNacimiento")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaNacimiento,
	        @RequestParam("cboSector") int sectorId,
	        @RequestParam("txtFechaIngreso")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaIngreso,
	        @RequestParam("txtFechaDefuncion")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaDefuncion)
	{
		String mensaje ="Error al agregar";
		
		Sector sector = new Sector();
		sector.setId(sectorId);
		
		Tipo tipo = new Tipo();
		tipo.setId(tipoId);
		
		Animal animal = new Animal();
		animal.setNombre(nombre);
		animal.setPeso(peso);
		animal.setTipo(tipo);
		animal.setGenero(genero);
		animal.setFechaNacimiento(fechaNacimiento);
		animal.setSector(sector);
		animal.setFechaIngreso(fechaIngreso);
		animal.setFechaDefuncion(fechaDefuncion);
		
		Animal a = aDAO.crud().save(animal);
		
		if(a!=null)
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
    	Animal a =null;
    	try 
    	{
    		a = aDAO.crud().findById(id).get();
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El animal no existe");
			return "redirect:listar";
		}
        
    	model.addAttribute("a", a);
    	model.addAttribute("tipos", tDAO.crud().findAll());
		model.addAttribute("sectores", sDAO.crud().findAll());
		
    	return "modificar.html";
    }
    
    @PostMapping("/actualizar")
	public String actualizar(Model model, RedirectAttributes ra,
			@RequestParam("txtId") int id,
			@RequestParam("txtNombre")String nombre,
			@RequestParam("txtPeso")int peso,
			@RequestParam("cboTipo") int tipoId,
			@RequestParam("txtGenero")String genero,
			@RequestParam("txtFechaNacimiento")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaNacimiento,
	        @RequestParam("cboSector") int sectorId,
	        @RequestParam("txtFechaIngreso")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaIngreso,
	        @RequestParam("txtFechaDefuncion")
	        @DateTimeFormat(pattern="yyyy-MM-dd")Date fechaDefuncion)
	{
		String mensaje ="Error al modificar";
		
		Sector sector = new Sector();
		sector.setId(sectorId);
		
		Tipo tipo = new Tipo();
		tipo.setId(tipoId);
		
		try 
    	{
			Animal animal = aDAO.crud().findById(id).get();
			animal.setNombre(nombre);
			animal.setPeso(peso);
			animal.setTipo(tipo);
			animal.setGenero(genero);
			animal.setFechaNacimiento(fechaNacimiento);
			animal.setSector(sector);
			animal.setFechaIngreso(fechaIngreso);
			animal.setFechaDefuncion(fechaDefuncion);
			Animal a = aDAO.crud().save(animal);
			
			if(a!=null)
			{
				mensaje= " Modificado correctamente";
			}
		} 
    	catch (Exception ex) 
    	{
			ra.addFlashAttribute("mensaje", "El animal no existe");
			return "redirect:listar";
		}
		
		ra.addFlashAttribute("mensaje", mensaje);
		
		return "redirect:listar";
	}
}
