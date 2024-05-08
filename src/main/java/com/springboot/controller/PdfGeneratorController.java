package com.springboot.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.springboot.model.Customer;
import com.springboot.service.ConversionService;
import com.springboot.service.CustomerService;
import com.springboot.service.PdfGeneratorService;

@Controller
public class PdfGeneratorController {

	@Autowired
	private CustomerService customerService;

	@Autowired
	private PdfGeneratorService pdfGeneratorService;
	
	@Autowired
    private ConversionService conversionService;

	@GetMapping("/customerForm")
	public String customerForm() {
		return "customerForm";
	}

	@PostMapping("/addCustomer")
	public String addCustomer(@ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
		try {
			customer.setDateOfBirth(conversionService.parseDate(customer.getDateOfBirthString()));
			customerService.saveCustomer(customer);
			String successMessage = "Customer added successfully! ";
			redirectAttributes.addFlashAttribute("successMessage", successMessage);
			return "redirect:/success";
		} catch (Exception e) {
			e.printStackTrace(); // Log the error
			String errorMessage = "Failed to add customer: " + e.getMessage();
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/error";
		}
	}

	@GetMapping("/getEmailToCreatePdf")
	public String getEmail() {
		return "getEmail";

	}

	@PostMapping("/create-Pdf")
	public String createPdf(@ModelAttribute("email") String email, RedirectAttributes redirectAttributes) {
		try {
			Customer customer = customerService.getCustomerById(email);
			String password = customerService.generatePassword(customer.getContactName(), customer.getDateOfBirth());
			Map<String, Object> data = pdfGeneratorService.generateData(customer);
			pdfGeneratorService.generatePdfFile("quotation", data, customer.getContactName() + ".pdf", password);
			String successMessage = "Pdf created successfully! ";
			redirectAttributes.addFlashAttribute("successMessage", successMessage);
			return "redirect:/success";
		} catch (Exception e) {
			e.printStackTrace(); // Log the error
			String errorMessage = "Failed to create pdf: " + e.getMessage();
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
			return "redirect:/error";
		}
	}
	
	@GetMapping("/success")
	public String showSuccessPage() {
		return "success"; // Return the success page
	}

	@GetMapping("/error")
	public String showErrorPage(Model model) {
		return "error"; // Return the error page
	}
}
