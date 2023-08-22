import javax.swing.JFrame;

public class TetrisGame {
	private TetrisEngine controller;
	private TetrisGrid model;
	private TetrisView view;
	private JFrame jFrame;
	private int width;
	private int height;
	TetrisGame(int width, int height) {
		this.width= width;
		this.height= height;
		initMVC();
		initJFrame();
		new Thread(controller).start();
		}
	private void initMVC() {
		view = new TetrisView(width, height, TetrisGrid.GRID_WIDTH, TetrisGrid.GRID_HEIGHT);
		model = new TetrisGrid();
		controller = new TetrisEngine(model, view);
		}
	private void initJFrame() {
		jFrame= new JFrame();
		jFrame.add(view);
		jFrame.pack();jFrame.setTitle("Tetris");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		}
	
	public static void main(String[] args) {
		new TetrisGame(400,600);
	}
}














