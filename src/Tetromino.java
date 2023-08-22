import java.awt.Color;
import java.awt.Point;

public abstract class Tetromino{
	protected Point[] points; // center를제외한나머지좌표3개, center로부터의상대적좌표
	protected Color color;
	protected int posX, posY; // center position of Tetrominoat grid
	
	Tetromino(int x, int y, Color color) {
		this.posX= x;
		this.posY= y;
		this.color= color;
		initPoints();
		}
	
	public abstract boolean rotateSelf(Color[][]grid);
	
	public abstract void initPoints();
	
	public Color[][] asArray() {
		Color[][] array= new Color[4][4];
		final int CENTER_Y= 1;
		final int CENTER_X= 2;
		array[CENTER_Y][CENTER_X] = color;
		for(Point point: points) {
			array[CENTER_Y + (int) point.getY()][CENTER_X + (int) point.getX()] = color;
		}
		return array;
	}
	
	public boolean moveLeft(Color[][]grid){
		boolean moved;
		int oldX=this.posX;
		this.posX=this.posX-1;
		if(this.isValidPos(grid)){
			moved=true;
			}else{
				this.posX=oldX;
				moved=false;
				}
		return moved;
		}
	
	public boolean moveRight(Color[][]grid){
		boolean moved;
		int oldX=this.posX;
		this.posX=this.posX+1;
		if(this.isValidPos(grid)){
			moved=true;
			}else{
				this.posX=oldX;
				moved=false;
				}
		return moved;
		}
	
	public boolean moveDown(Color[][]grid){
		int oldY=this.posY;
		this.posY=this.posY+1;
		boolean moved=false;
		if(this.isValidPos(grid)){
			moved=true;
			}else{
				this.posY=oldY;
				}
		return moved;
		}
	
	public void placeSelf(Color[][] grid) {
		grid[posY][posX] = this.color;
		for(Point point: points) {
			grid[posY+ (int) point.getY()][posX+ (int) point.getX()] = this.color;
		}
	}
	public void placeGhost(Color[][]grid){
		//원래블록데이터백업
		int thisX=this.posX;
		int thisY=this.posY;
		Color thisColor=this.color;
		//고스트블록생성
		this.color=Color.GRAY;
		dropSelf(grid);
		placeSelf(grid);
		//원래블록데이터되돌림
		this.color=thisColor;
		this.posX=thisX;
		this.posY=thisY;
		}
	
	public void dropSelf(Color[][]grid){
		boolean movedDown;
		do{movedDown=this.moveDown(grid);
		}
		while(movedDown);
		}
	
	public boolean isValidPos(Color[][]grid){
		if(!isValidPos(grid,posX,posY))
			return false;
		else{
			int gridX,gridY;
			for(Point point:points){
				if(!isValidPos(grid,posX+(int)point.getX(),posY+(int)point.getY()))
					return false;
				}
			}
		return true;
		}
	
	private boolean isValidPos(Color[][]grid,int x,int y){
		if(y<0||y>=grid.length){
			return false;
			}else if(x<0||x>=grid[0].length){
				return false;
				}else if(grid[y][x]!=null){
					return false;
					}
				else 
					return true;
				}
	
}
	















