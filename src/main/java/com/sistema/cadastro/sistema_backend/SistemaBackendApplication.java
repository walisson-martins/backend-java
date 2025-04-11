package com.sistema.cadastro.sistema_backend;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.sistema.cadastro.sistema_backend.domain.Categoria;
import com.sistema.cadastro.sistema_backend.domain.Cidade;
import com.sistema.cadastro.sistema_backend.domain.Cliente;
import com.sistema.cadastro.sistema_backend.domain.Endereco;
import com.sistema.cadastro.sistema_backend.domain.Estado;
import com.sistema.cadastro.sistema_backend.domain.Produto;
import com.sistema.cadastro.sistema_backend.enums.TipoCliente;
import com.sistema.cadastro.sistema_backend.repositories.CategoriaRepository;
import com.sistema.cadastro.sistema_backend.repositories.CidadeRepository;
import com.sistema.cadastro.sistema_backend.repositories.ClienteRepository;
import com.sistema.cadastro.sistema_backend.repositories.EnderecoRepository;
import com.sistema.cadastro.sistema_backend.repositories.EstadoRepository;
import com.sistema.cadastro.sistema_backend.repositories.ProdutoRepository;

@SpringBootApplication
public class SistemaBackendApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public static void main(String[] args) {

		SpringApplication.run(SistemaBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Notebook", 800.00);
		Produto p3 = new Produto(null, "mouse", 80.00);

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "5615484212", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("2765655332", "56455124"));

		Endereco e1 = new Endereco(null, "Rua flores", "300", "APTO 303", "Jardim", "3822034", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "45", "APTO 4", "Jardim", "3823234", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
	}

}
