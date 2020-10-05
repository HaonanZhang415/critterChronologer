package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Pet findPetById(long petId) {
        Optional<Pet> petResponse = petRepository.findById(petId);
        Pet pet = petResponse.get();
        return pet;
    }


    public Long savePet(Pet pet, Long ownerId) {
        Optional<Customer> customerResponse = customerRepository.findById(ownerId);
        Customer customer = customerResponse.get();
        pet.setCustomer(customer);
        Pet savedPet = petRepository.save(pet);
        List<Pet> pets = customer.getPets();
        if (pets == null) {
            pets = new ArrayList<Pet>();
        }
        pets.add(savedPet);
        customer.setPets(pets);
        customerRepository.save(customer);
        return savedPet.getId();
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    public List<Pet> findAllPetsByCustomer(long ownerId) {
        Optional<Customer> customerResponse = customerRepository.findById(ownerId);
        Customer customer = customerResponse.get();
        return petRepository.findPetsByOwnerId(customer.getId());
    }
}
