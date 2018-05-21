package br.ufsc.distribuida;

/**
 * Classe Monitora do algoritmo de Berkeley para sincronização.
 * Responsável pelos calculos e retornos
 * @author Matheus Duarte Dias
 * 2018-05-21 - Criciúma - SC - Brazil
 */
public class Monitor {
	private long serverTime;
	private long[] diffTimes;
	private long sumDiffs;
	private int numClients = 1; 
	private int countClientesOperados;

	public long getSumDiffs() {
		return sumDiffs;
	}

	public void setSumDiffs(long sumDiffs) {
		this.sumDiffs = sumDiffs;
	}

	public int getCountClientesOperados() {
		return countClientesOperados;
	}

	public void setCountClientesOperados(int countClientesOperados) {
		this.countClientesOperados = countClientesOperados;
	}

	public long getServerTime() {
		return serverTime;
	}

	public int getNumClients() {
		return numClients;
	}

	public Monitor(int numClients) {
		this.numClients = numClients;
		this.serverTime = 0;
		this.countClientesOperados = this.numClients;
		this.diffTimes = new long[this.numClients];
		this.sumDiffs = 0;
	}

	public synchronized void setServerTime(long serverTime) {
		this.serverTime = serverTime;

		try {
			// Notificar todos os clientes.
			notifyAll();
			// servidor aguarda
			wait();
		} catch (InterruptedException ignore) {
		}
	}

	public synchronized void setDiffTimes(int nCliente, long time) {
		try {

			// Se server não configurou, clientes aguardam na fila
			if (getServerTime() == 0) {
				wait();
			}

			this.diffTimes[nCliente] = (time - getServerTime());
			setSumDiffs(getSumDiffs() + this.diffTimes[nCliente]);
			countClientesOperados--;

			// Todos os clientes foram finalizados
			if (getCountClientesOperados() == 0) {
				notify();
			}

			// Aguardar servidor finalizar ajuste de tempo
			wait();
		} catch (InterruptedException e) {
		}
	}

	public synchronized void calcMedia() {
		long avg = (getSumDiffs() / (getNumClients() + 1));
		for (int i = 0; i < getNumClients(); i++) {
			this.diffTimes[i] = ((-this.diffTimes[i]) + avg);
		}
		notifyAll();
	}

	public synchronized long getNewTime(int posicao) {
		return this.diffTimes[posicao];
	}

	public synchronized long getMedia() {
		return getSumDiffs() / (getNumClients() + 1);
	}

	public synchronized void reiniciaProcesso() {
		this.serverTime = 0;
		this.countClientesOperados = this.numClients;
		this.sumDiffs = 0;
	}
}
