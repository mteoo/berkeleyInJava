package br.ufsc.distribuida;
/**
 * Implementação do algoritmo de Berkeley para sincronização de relógios lógicos
 * @author matheus.dias
 * 
 */
public class Main {
	private static final Integer NUMERO_CLIENTES = 1;
	
	public static void main(String[] args) {
		//Instancia monitor, server e filhos e inicia.
		Monitor monitor = new Monitor(NUMERO_CLIENTES);
		
		Server server = new Server(monitor);
		server.start();
		Cliente clientes[] = new Cliente[NUMERO_CLIENTES];
		
		for(int i=0;i<NUMERO_CLIENTES;i++){
			clientes[i] = new Cliente(i, monitor);
			clientes[i].start();
		}
	}
}
