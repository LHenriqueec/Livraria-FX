package threads;

public class ExportadorDeCSV implements Runnable {

	public void exportar() {
		System.out.println("Testando a Thread!");
	}

	@Override
	public void run() {
		exportar();
	}
}
