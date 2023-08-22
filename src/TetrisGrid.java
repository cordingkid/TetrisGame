import java.awt.Color;

//Tetris data class which is the model for Tetris MVC.
public class TetrisGrid{											//테트리스 그리드*************************************
			public static final int GRID_WIDTH= 10;
			public static final int GRID_HEIGHT= 22;
			private Color[][]grid;
			private Tetromino tetromino;
			private Tetromino nextTetromino;
			
			public TetrisGrid() {
				this.nextTetromino= TetrominoFactory.getNewTetromino();
				this.grid=new Color[GRID_HEIGHT][GRID_WIDTH];
				}
			
			public Color[][] getGridWithTetromino() {
				Color[][] gridWithTetromino= new Color[GRID_HEIGHT][GRID_WIDTH];
				for(int y=0;y<GRID_HEIGHT;y++){
					for(int x=0;x<GRID_WIDTH;x++){
						if(grid[y][x]!=null)gridWithTetromino[y][x]=new Color(grid[y][x].getRGB());
						}
					}
				if(hasTetromino()){
					this.tetromino.placeGhost(gridWithTetromino);
					this.tetromino.placeSelf(gridWithTetromino);
					}
				return gridWithTetromino;
				}
			
			public boolean hasTetromino(){
				return this.tetromino!=null;
				}
			
			public boolean moveTetrominoDown(){
				if(hasTetromino()){
					return this.tetromino.moveDown(this.grid);
					}else{
						return false;	//블록이바닦에고정되고새로운블록이생기기전Down을눌렀을경우
						}
				}
					
			public boolean moveTetrominoLeft(){
				if(hasTetromino()){
					return this.tetromino.moveLeft(this.grid);
					}else{
						return false;
						}
				}
			
			
			public boolean moveTetrominoRight(){
				if(hasTetromino()){
					return this.tetromino.moveRight(this.grid);
					}else{
						return false;
						}
				}
			
			
			public boolean rotateTetromino(){
				if(hasTetromino()){
					return this.tetromino.rotateSelf(this.grid);
					}else{
						return false;
						}
				}
			
			
			public void placeTetromino(){
				if(hasTetromino()){
					this.tetromino.placeSelf(this.grid);
					this.tetromino=null;	//새로운Tetromino가필요한것을알리기위해null처리함
				}
			}
			
			public boolean dropTetromino(){
				if(hasTetromino()){
					this.tetromino.dropSelf(this.grid);
					return true;
					}else{
						return false;
						}
				}
			
			public boolean getNewTetromino() {
				boolean spawned=false;
				tetromino=nextTetromino;
				nextTetromino=TetrominoFactory.getNewTetromino();
				if(tetromino.isValidPos(grid)){	//새로운Tetromino를만들수있는지판단
					spawned=true;
					}else{	//화면에꽉찾을경우
						tetromino=null;
						}
				return spawned;
				}
			
			public int handleAllFilledRows(){
				int filled=0;
				int row=GRID_HEIGHT-1;
				int topRow=0;
				while(row>=topRow){
					if(isFilledRow(row)){
						filled=filled+1;
						shiftRows(row,topRow);
						topRow=topRow+1;
						}else{
							row=row-1;
							}
					}
				return filled;
				}
			
			private boolean isFilledRow(int row){
				for(int col=0;col<GRID_WIDTH;col++){
					if(grid[row][col]==null){
						return false;
						}
					}
				return true;
				}
			
			private void shiftRows(int row,int topRow){
				for(;row>topRow;row--){
					this.grid[row]=this.grid[row-1].clone();
					}
				this.grid[topRow]=new Color[this.GRID_WIDTH];
				}
			
			public Color[][] getNextTetrominoGrid() {
				return this.nextTetromino.asArray();
				}
}















