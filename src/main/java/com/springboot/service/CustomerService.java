package com.springboot.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.springboot.model.Customer;
import com.springboot.repository.CustomerRepository;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	public Customer saveCustomer(Customer customer)
	{
		System.out.println(customer);
		return customerRepository.save(customer);
	}
	
	public List<Customer> saveCustomers(List<Customer> customers)
	{
		return customerRepository.saveAll(customers);
	}
	
	public List<Customer> getCustomers()
	{
		return customerRepository.findAll();
	}
	
	public Customer getCustomerById(String email)
	{
		return customerRepository.findById(email).orElse(new Customer());
	}
	
	public String generatePassword(String contactName, Date dateOfBirth) {
        // Extract the first four characters from the contactName
        String contactPrefix = contactName.substring(0, Math.min(contactName.length(), 4));

        // Format the dateOfBirth as ddMMyyyy
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String dobString = dateFormat.format(dateOfBirth);

        // Concatenate the contact prefix and dob string
        String password = contactPrefix + dobString;

        return password;
    }

}
