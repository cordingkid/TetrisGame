import java.awt.event.KeyEvent;

//Tetris engine class which is the controller for Tetris MVC.
public class TetrisEngine implements Runnable{								//테트리스 엔진********************************************
			private TetrisGrid model;
			private TetrisView view;
			private long score;
			private int freeFallIterations;
			public TetrisEngine(TetrisGrid model, TetrisView view) {
				this.model= model;
				this.view= view;
				score=0;
				freeFallIterations=0;
				}
		
	// Main game loop
	// Updates the grid for the view. Moves Tetrominoif any. If none, creates new and updates score.
	@Override
	public void run() {
		boolean canMoveDown;
		boolean gameContinues= model.getNewTetromino();
		this.updateViewGrid();
		this.updateViewNextTetromino();
		long cycleStartTime=System.currentTimeMillis();
		while(gameContinues) {
			try{
				Thread.sleep(50);
			} catch(InterruptedException ex) {
				ex.printStackTrace();
				}
			
		
		if((System.currentTimeMillis()-cycleStartTime)>=500){
			if(model.hasTetromino()){
				canMoveDown=model.moveTetrominoDown();
				if(!canMoveDown){
					//블록을바닥에고정
					this.placeTetromino();
					} else {
						freeFallIterations+=1;
						}
				} else {
					gameContinues=model.getNewTetromino();
					updateViewNextTetromino();
					}
			this.updateViewGrid();
			cycleStartTime=System.currentTimeMillis();
			}else{
				userActionHandler();
				}
		}
	view.setGameOver();
	}
	private void updateViewGrid() {
		this.view.updateGrid(this.model.getGridWithTetromino());
		}
	
	// Method to update next tetrominostored in view.
	private void updateViewNextTetromino() {
		this.view.updateNextTetrominoGrid(this.model.getNextTetrominoGrid());
		}
	
	private void dropTetromino(){
		if(this.model.dropTetromino()){
			placeTetromino();
			}
		}
	
	private void placeTetromino(){
		this.model.placeTetromino();
		score=score+100*this.model.handleAllFilledRows();
		this.updateScore();
		this.updateViewGrid();
		}
	
	private void moveTetrominoLeft(){
		if(this.model.moveTetrominoLeft()){
			this.updateViewGrid();
			}
		}
	
	private void moveTetrominoRight(){
		if(this.model.moveTetrominoRight()){
			this.updateViewGrid();
			}
		}
	
	private void rotateTetromino(){
		if(this.model.rotateTetromino()){
			this.updateViewGrid();
			}
		}
	
	private void updateScore(){
		this.score+=24-freeFallIterations;
		freeFallIterations=0;
		this.view.setScore(score);
		}
	
	private void userActionHandler(){
		int command=this.view.getLastCommand();
		switch(command){
		case KeyEvent.VK_DOWN:
			this.dropTetromino();
			break;
		case KeyEvent.VK_LEFT:
			this.moveTetrominoLeft();
			break;
		case KeyEvent.VK_RIGHT:
			this.moveTetrominoRight();
			break;
		case KeyEvent.VK_UP:
			this.rotateTetromino();
			break;
		default:
			break;
			}
		}
	}
















