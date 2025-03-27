package com.backend.aplicacao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.backend.dominio.Pessoa;

/**
 * Main application class that demonstrates the use of JPA for database
 * operations.
 */
public class Programa {

	/**
	 * Main method to execute the program.
	 * 
	 * @param args Command line arguments.
	 */
	public static void main(String[] args) {
		// Creating an EntityManagerFactory instance.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bd-jpa");
		// Creating an EntityManager instance.
		EntityManager em = emf.createEntityManager();

		Pessoa p = em.find(Pessoa.class, 2);
		em.getTransaction().begin();
		em.remove(p);
		em.getTransaction().commit();

		System.out.println("Ready");
		em.close();
		emf.close();
	}
}
