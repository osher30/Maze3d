
package view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	private Maze3d maze;
	private Character character;
	
	public void setStartPositionCharacter(Position pos){
		character.setPos(pos);
	}

	public void setMaze(Maze3d maze){
		this.maze = maze;
	}
	
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		this.redraw();
	}

	public MazeDisplay(Composite parent, int style) {

		super(parent, style);
		character = new Character();
						
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				Position pos = character.getPos();
				List<Position> moves = maze.getAllPosibleMoves(pos);
				
				switch (e.keyCode) {
				case SWT.ARROW_RIGHT:
					if (moves.contains(new Position(pos.getZ(), pos.getY(), pos.getX() + 1)))
					{
					character.setPos(new Position(pos.getZ(), pos.getY(), pos.getX() + 1));
					character.moveRight();
					redraw();
					}
					break;
				
				case SWT.ARROW_LEFT:	
					if (moves.contains(new Position(pos.getZ(), pos.getY(), pos.getX() - 1)))
							{
					character.setPos(new Position(pos.getZ(), pos.getY(), pos.getX() - 1));
					character.moveLeft();
					redraw();
							}
					break;
				
				case SWT.ARROW_UP:	
					if (moves.contains(new Position(pos.getZ(), pos.getY() - 1, pos.getX())))
							{
					character.setPos(new Position(pos.getZ(), pos.getY() - 1, pos.getX()));
					character.moveForward();
					redraw();
							}
					break;
				case SWT.ARROW_DOWN:		
					if (moves.contains(new Position(pos.getZ(), pos.getY() + 1, pos.getX())))
					{
					character.setPos(new Position(pos.getZ(), pos.getY() + 1, pos.getX()));
					character.moveBackward();
					redraw();
					}
					break;
				case SWT.PAGE_UP:	
					if (moves.contains(new Position(pos.getZ() + 1, pos.getY(), pos.getX())))
					{
					character.setPos(new Position(pos.getZ() + 1, pos.getY(), pos.getX()));
					character.moveUp();
					redraw();
					}
					break;
				case SWT.PAGE_DOWN:		
					if (moves.contains(new Position(pos.getZ() - 1, pos.getY(), pos.getX())))
					{
					character.setPos(new Position(pos.getZ() - 1, pos.getY(), pos.getX()));
					character.moveDown();
					redraw();
					}
					break;
				default:
					break;	
				}
			}
		});
		
		this.addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				e.gc.setForeground(new Color(null,0,0,0));
				   e.gc.setBackground(new Color(null,0,0,0));
				   
				   if(mazeData != null){
					   
					   int width=getSize().x;
					   int height=getSize().y;

					   int w=width/mazeData[0].length;
					   int h=height/mazeData.length;

					   for(int i=0;i<mazeData.length;i++)
						   for(int j=0;j<mazeData[i].length;j++){
							   int x=j*w;
							   int y=i*h;
							   if(mazeData[i][j]!=0)
								   e.gc.fillRectangle(x,y,w,h);
						   }
					   character.draw(w, h, e.gc);
				   	}
			}
		});
		
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {	
				getDisplay().syncExec(new Runnable() {					

					@Override
					public void run() {
						
						redraw();
					}
				});
				
			}
		};
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(task, 0, 500);
	}

	public void takeCharacterToExit(List<Position> solution) {
		for(Position pos : solution){
		character.setPos(new Position(pos.getZ(), pos.getY(), pos.getX()));
		redraw();
		}
	}
}




