package threads;

public class TestandoThreadMain {
	public static void main(String[] args) {
		ExportadorDeCSV exportador = new ExportadorDeCSV();
		Thread t = new Thread(exportador);
		t.start();
	}
}
