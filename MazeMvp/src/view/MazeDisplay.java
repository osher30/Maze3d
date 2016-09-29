
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
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.serach.Solution;
import algorithms.serach.State;
import presenter.CommandsManager.solveMaze;

public class MazeDisplay extends Canvas {
	
	private int[][] mazeData;
	private Maze3d maze;
	private Character character;
	private int currFloor;
	private Position start;
	private Position goal;
	Image img1;
	
	
	public void setStartPositionCharacter(Position pos){
		character.setPos(pos);
	}

	public void setMaze(Maze3d maze){
		this.maze = maze;
		this.start=maze.getStartPosition();
		this.goal=maze.getGoalPosition();
		this.currFloor=start.getZ();
		this.setMazeData(maze.getCrossSectionByZ(currFloor));
	}
	
	public void setMazeData(int[][] mazeData) {
		this.mazeData = mazeData;
		//this.redraw();
	}

	public MazeDisplay(Composite parent, int style) {

		super(parent, style);
		character = new Character();
		img1=new Image(null,"lib/images/mushroom.jpg");		
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
					currFloor++;
					setMazeData(maze.getCrossSectionByZ(currFloor));
					redraw();
					}
					break;
				case SWT.PAGE_DOWN:		
					if (moves.contains(new Position(pos.getZ() - 1, pos.getY(), pos.getX())))
					{
					character.setPos(new Position(pos.getZ() - 1, pos.getY(), pos.getX()));
					character.moveDown();
					currFloor--;
					setMazeData(maze.getCrossSectionByZ(currFloor));
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
							   if(mazeData[i][j]!=0){
								   e.gc.fillRectangle(x,y,w,h);
							   }
						   }
					   //ARROWS
					   character.draw(w, h, e.gc);
					   e.gc.drawImage(img1, 0, 0, img1.getBounds().width, img1.getBounds().height,w*goal.getX()
							   ,goal.getY()*h, w, h);
					   
					   //HINT
					//   IF(FLAG==1){
						   //DRAW CLUE
					   //FLAG=0;
					//   }
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

	public void takeCharacterToExit(Solution<Position> solution) {
		List<State<Position>> list = solution.getStates();
		list.remove(0);
		
		
		TimerTask task=new TimerTask(){

			@Override
			public void run() {
				getDisplay().syncExec(new Runnable() {
					
					@Override
					public void run() {
						if(!list.isEmpty()){
							State<Position> state=list.get(0);
							if(state.getValue().getZ()<character.getPos().getZ()){
								currFloor--;
								setMazeData(maze.getCrossSectionByZ(currFloor));
							}
							else if(state.getValue().getZ()>character.getPos().getZ()){
								currFloor++;
								setMazeData(maze.getCrossSectionByZ(currFloor));
							}
							
							character.setPos(state.getValue());
							list.remove(0);
							redraw();
							/*for(State<Position> pos : list){
								Position pos1 = pos.getValue();
								character.setPos(new Position(pos1.getZ(), pos1.getY(), pos1.getX()));
								redraw();
								inde
								x++;*/
							
				        }
						else{
							cancel();
						}
				}
			});
		}
		
	};
	Timer timer=new Timer();
	timer.scheduleAtFixedRate(task,0,500);
	}
}




