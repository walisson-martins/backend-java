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
		// Creating instances of Pessoa to be persisted in the database.
		Pessoa p1 = new Pessoa(null, "Carlos da Silva", "carlos2211@gmail.com");
		Pessoa p2 = new Pessoa(null, "Antonio Carlos", "antonioqw2@gmail.com");
		Pessoa p3 = new Pessoa(null, "Jessica Santos", "jessicas23@gmail.com");

		// Creating an EntityManagerFactory instance.
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("bd-jpa");
		// Creating an EntityManager instance.
		EntityManager em = emf.createEntityManager();
		// Starting a transaction.
		em.getTransaction().begin();

		// Persisting the Pessoa instances in the database.
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		// Committing the transaction.
		em.getTransaction().commit();
		System.out.println("Ready");
	}
}
