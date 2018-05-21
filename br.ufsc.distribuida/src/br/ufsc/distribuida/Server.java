package br.ufsc.distribuida;

import java.sql.Date;

/**
 * Classe server do algoritmo de Berkeley para sincronização.
 * @author Matheus Duarte Dias
 * 2018-05-21 - Criciúma - SC - Brazil
 */
public class Server  extends Thread{
	private Monitor monitor;
	private final int sleepInMs;
	private long serverTime;
	
	public Monitor getMonitor() {
		return monitor;
	}

	public long getServerTime() {
		return serverTime;
	}


	public void setServerTime(long serverTime) {
		this.serverTime = serverTime;
	}


	public int getSleepInMs() {
		return sleepInMs;
	}


	public Server(Monitor monitor) {
		this.monitor = monitor;
		this.sleepInMs = 10000;
		this.serverTime = System.nanoTime();
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		while(true){
			try {
				//Server dorme
				Thread.sleep(getSleepInMs());
				
				System.out.println("[Server: OK] Tempo: " +  new Date(getServerTime()).toGMTString());
				getMonitor().setServerTime(getServerTime());
				
				System.out.println("################### Iniciando atualização dos relógios...");
				//Calcula timestamp médio e seta
				getMonitor().calcMedia();
				setServerTime(getServerTime() + getMonitor().getMedia());
				
				System.out.println("Nova temporização acordada: " + new Date(getServerTime()).toGMTString());
				getMonitor().reiniciaProcesso();
			} catch (InterruptedException ignore) {
			}
		}
	}
}
