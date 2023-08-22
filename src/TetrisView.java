import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class TetrisView extends JPanel{							//테트리스 뷰******************************************
			private static final double GAME_SCORE_AREA_RATIO= 0.75;
			private int tileSize;
			private int nextTetrominoTileSize;
			private Color[][] grid;
			private Color[][] nextTetrominoGrid;
			private int gridWidth;
			private int gridHeight;
			private int width;
			private int height;
			private int gameWidth;
			private int sideWidth;
			private boolean gameOver;
			private int lastCommand;
			private long score;
			
			public TetrisView(int width, int height, int gridWidth, int gridHeight) {
				this.gridWidth=gridWidth;
				this.gridHeight=gridHeight;
				initDimensions(width,height);
				initTileSize();
				revisitDimensions();
				addKeyBindings();
				initView();
				resetLastCommand();
				this.setPreferredSize(new Dimension(this.width,this.height));
				}
			
			// initTileSize()에서구한tileSize를기반으로height, gameWidth를다시계산하고, gameWidth가변경될
			// 경우width도다시구함
			private void revisitDimensions() {
				this.height= tileSize* (gridHeight-2);
				this.gameWidth= tileSize* gridWidth;
				this.width= gameWidth+ sideWidth+ 1;
				}
			
			private void initDimensions(int width, int height) {
				this.width= width;
				this.height= height;
				this.gameWidth= (int) Math.floor(width * GAME_SCORE_AREA_RATIO);
				this.sideWidth= width -this.gameWidth-1;
				}
			
			private void initTileSize() {
				int xSize= gameWidth/ gridWidth;
				int ySize= height / gridHeight;
				if(xSize< ySize) {
					tileSize= xSize;
				} else{
					tileSize= ySize;
				}
				nextTetrominoTileSize= sideWidth/ 6;
				}
			
			private void initView() {
				this.grid= new Color[gridHeight][gridWidth];
				this.nextTetrominoGrid= new Color[4][4];
				gameOver=false;
				score=0;
				}
			
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2d= (Graphics2D) g;
				
				if(gameOver){
					drawFinalScreen(g2d);
					}else{
						drawGameGrid(g2d);
						fillSidePanel(g2d);
						drawScore(g2d);
						drawNextTetrominoPanel(g2d);
						}
				}
			
			private void drawNextTetrominoPanel(Graphics2D g2d) {
				g2d.setColor(new Color(255, 200, 0));
				g2d.drawString("NEXT", this.width-this.sideWidth+ nextTetrominoTileSize,this.height-6* nextTetrominoTileSize);
				
				int x, y;
				for(int row= 0; row < 4; row++) {
					for(int col= 0; col < 4; col++) {
						if(nextTetrominoGrid[row][col] != null) {
							g2d.setPaint(nextTetrominoGrid[row][col]);
							} else{
								g2d.setPaint(Color.BLACK);
								}
						y = this.height-nextTetrominoTileSize-(4-row) * nextTetrominoTileSize;
						x = this.width-this.sideWidth+ nextTetrominoTileSize+ col * nextTetrominoTileSize;
						g2d.fillRect(x, y, nextTetrominoTileSize, nextTetrominoTileSize);
						g2d.setPaint(Color.LIGHT_GRAY);
						g2d.drawRect(x, y, nextTetrominoTileSize, nextTetrominoTileSize);
						}
					}
				}
			
			private void fillSidePanel(Graphics2D g2d) {
				g2d.setPaint(new Color(0, 8, 52)); // 군청색
				g2d.fillRect(width -sideWidth, 0, sideWidth, height);
				}
			
			private void drawScore(Graphics2D g2d) {
				g2d.setColor(new Color(255, 233, 0));
				FontMetrics metrics= g2d.getFontMetrics(g2d.getFont());
				g2d.drawString("SCORE:", this.width-this.sideWidth, metrics.getHeight());
				String scoreString= Long.toString(0);
				g2d.drawString(scoreString, this.width-this.sideWidth, metrics.getHeight() * 2);
				}
			
			private void drawGameGrid(Graphics2D g2d) {
				for(int row= 2; row < gridHeight; row++) {
					for(int col= 0; col < gridWidth; col++) {
						if(grid[row][col] != null) {
							g2d.setPaint(grid[row][col]);
							} else{
								g2d.setPaint(Color.BLACK);
								}
						g2d.fillRect(col * tileSize, (row -2) * tileSize, tileSize, tileSize);
						g2d.setPaint(Color.WHITE);
						g2d.drawRect(col * tileSize, (row -2) * tileSize, tileSize, tileSize);
						}
					}
				}
			
			private void drawFinalScreen(Graphics2D g2d){
				g2d.setColor(Color.BLACK);
				g2d.fill(this.getBounds());
				g2d.setFont(g2d.getFont().deriveFont(40f));
				g2d.setColor(Color.RED);
				FontMetrics metrics=g2d.getFontMetrics(g2d.getFont());
				g2d.drawString("GAMEOVER!",(width-metrics.stringWidth("GAMEOVER!"))/2,height/2);
				g2d.setFont(g2d.getFont().deriveFont(20f));
				metrics=g2d.getFontMetrics(g2d.getFont());
				String scoreString="Score:"+score;
				g2d.drawString(scoreString,(width-metrics.stringWidth(scoreString))/2,
						height/2+metrics.getHeight()*2);
				}
			
			public void updateGrid(Color[][] grid) {
				arrayCopy(grid, this.grid);
				this.repaint();
				}
			// clone()을이용한배열복사는1차원만가능하므로2차원배열복사를위한함수를별도제작
			private void arrayCopy(Color[][] array, Color[][] copyArray) {
				for(int i= 0; i< array.length; i++) {
					copyArray[i] = array[i].clone();
					}
				}
			public void updateNextTetrominoGrid(Color[][] nextTetrominoGrid) {
				arrayCopy(nextTetrominoGrid, this.nextTetrominoGrid);
				this.repaint();
				}
			
			public void setScore(long score){
				this.score=score;
				}
			
			public void setGameOver(){
				gameOver=true;
				this.repaint();
				}
			
			private void setLastCommand(int command){
				lastCommand=command;
				}
			
			public int getLastCommand(){
				int command=lastCommand;
				resetLastCommand();
				return command;
				}
			
			private void resetLastCommand(){
				lastCommand = KeyEvent.VK_UNDEFINED;
				}
			
			private void addKeyBinding(int keyCode,ActionListener actionListener){
				getInputMap().put(KeyStroke.getKeyStroke(keyCode,0,false),String.valueOf(keyCode));
				getActionMap().put(String.valueOf(keyCode),new AbstractAction(){
					public void actionPerformed(ActionEvent e){
						actionListener.actionPerformed(e);
						}
					});
				}
			
			private void addKeyBindings(){
				addKeyBinding(KeyEvent.VK_DOWN,e ->setLastCommand(KeyEvent.VK_DOWN));
				addKeyBinding(KeyEvent.VK_LEFT,e ->setLastCommand(KeyEvent.VK_LEFT));
				addKeyBinding(KeyEvent.VK_RIGHT,e ->setLastCommand(KeyEvent.VK_RIGHT));
				addKeyBinding(KeyEvent.VK_UP,e ->setLastCommand(KeyEvent.VK_UP));
				}
		}







