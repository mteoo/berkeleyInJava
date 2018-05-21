package br.ufsc.distribuida;

import java.sql.Date;

/**
 * Classe cliente do algoritmo de Berkeley para sincronização.
 * @author Matheus Duarte Dias
 * 2018-05-21 - Criciúma - SC - Brazil
 */
public class Cliente extends Thread {

	private int iCliente;
	private long timeCliente;
	private Monitor monitor;


	public int getICliente() {
		return iCliente;
	}

	public void setICliente(int iCliente) {
		this.iCliente = iCliente;
	}

	public long getTimeCliente() {
		return timeCliente;
	}

	public void setTimeCliente(long tCliente) {
		this.timeCliente = tCliente;
	}

	public Monitor getMonitor() {
		return monitor;
	}

	public Cliente(int iCliente, Monitor monitor) {
		this.iCliente = iCliente;
		this.monitor = monitor;
		this.timeCliente = System.nanoTime();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while(true){			
			System.out.println("[Cliente: " + getICliente() + "] Tempo: " + new Date(getTimeCliente()).toGMTString());
			
			//Setar as diferenças, caso o server não tenha configurado set tempo os clientes dormem.
			getMonitor().setDiffTimes(getICliente(), getTimeCliente());
			
			//Atualizar relogio do cliente
			setTimeCliente(getTimeCliente() + getMonitor().getNewTime(getICliente()));
			System.out.println("[Cliente: " + getICliente() + "] Temporização atualizada: " +  new Date(getTimeCliente()).toGMTString());
			System.out.println("################### Atualização concluída...");
		}
	}

}
