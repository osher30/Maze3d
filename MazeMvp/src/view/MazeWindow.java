package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import algorithms.mazeGenerators.Maze3d;

public class MazeWindow extends BasicWindow implements View {

	private MazeDisplay mazeDisplay;
	private String mazeName;
	private String cmd = "";
	
	@Override
	protected void initWidgets() {
		GridLayout gridLayout = new GridLayout(2, false);
		shell.setLayout(gridLayout);	
		shell.setText("Chen & Osher Maze3D");
		shell.setBackground(new Color(null,240,200,255));
		
		Composite btnGroup = new Composite(shell, SWT.BORDER);
		RowLayout rowLayout = new RowLayout(SWT.VERTICAL);
		btnGroup.setLayout(rowLayout);
		btnGroup.setBackground(new Color(null,240,100,255));
		
		Button btnGenerateMaze = new Button(btnGroup, SWT.PUSH);
		btnGenerateMaze.setText("Generate maze");	
		btnGenerateMaze.setBackground(new Color(null,200,255,100));
		
		btnGenerateMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showGenerateMazeOptions();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Button btnDisplayMaze = new Button(btnGroup, SWT.PUSH);
		btnDisplayMaze.setText("Display maze");	
		btnGenerateMaze.setBackground(new Color(null,200,255,100));
		
		btnDisplayMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				showDisplayMazeOptions();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Button btnSolveMaze = new Button(btnGroup, SWT.PUSH);
		btnSolveMaze.setText("Solve maze");
		btnGenerateMaze.setBackground(new Color(null,200,255,100));
		
		btnSolveMaze.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mazeName != null) {
					cmd = "display_solution";
					setChanged();
					notifyObservers("solve " + mazeName);
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
			}
		});
		
		Button btnHint = new Button(btnGroup, SWT.PUSH);

		btnHint.setText("Show Hint");
		btnHint.setBackground(new Color(null,102,178,255));
		btnHint.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mazeName != null) {
					cmd = "display_hint";
					setChanged();
					notifyObservers("solve " + mazeName);
				}

			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		
		Button btnExitMaze = new Button(btnGroup, SWT.PUSH);
		btnExitMaze.setText("Exit");
		
		
		btnExitMaze.setBackground(new Color(null,102,178,255));
		btnExitMaze.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				exitEvent(null);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});
		
		mazeDisplay = new MazeDisplay(this.shell, SWT.DOUBLE_BUFFERED);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

	}
	

	
	protected void showGenerateMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Generate Maze");
		shell.setSize(300, 260);
		
		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);
		
		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
		Text txtName = new Text(shell, SWT.BORDER);
		
		Label lblFloors = new Label(shell, SWT.NONE);
		lblFloors.setText("Floors: ");
		Text txtFloors = new Text(shell, SWT.BORDER);
		
		Label lblRows = new Label(shell, SWT.NONE);
		lblRows.setText("Rows: ");
		Text txtRows = new Text(shell, SWT.BORDER);
		
		Label lblCols = new Label(shell, SWT.NONE);
		lblCols.setText("Cols: ");
		Text txtCols = new Text(shell, SWT.BORDER);
		
		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Generate");
		
		btnGenerate.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("generate_maze" +txtName.getText() + " " + txtFloors.getText()+ 
						" " + txtRows.getText() + " " + txtCols.getText());
				shell.close();
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		//mazeDisplay = new MazeDisplay(shell, SWT.NONE);			
		shell.open();		
	}

	protected void showDisplayMazeOptions() {
		Shell shell = new Shell();
		shell.setText("Display Maze");
		shell.setSize(300, 200);

		GridLayout layout = new GridLayout(2, false);
		shell.setLayout(layout);

		Label lblName = new Label(shell, SWT.NONE);
		lblName.setText("Name: ");
	
		Text txtName = new Text(shell, SWT.BORDER);

		Button btnGenerate = new Button(shell, SWT.PUSH);
		btnGenerate.setText("Display");
		btnGenerate.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent arg0) {
				setChanged();
				notifyObservers("display_maze " + txtName.getText());
				shell.close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

		shell.open();
	}

	@Override
	public void notifyMazeIsReady(String name) {
		display.syncExec(new Runnable() {
			
			@Override
			public void run() {
				MessageBox msg = new MessageBox(shell);
				msg.setMessage("Maze " + name + " is ready");
				msg.open();	
				
				setChanged();
				notifyObservers("display_maze " + name);
				//mazeName = name;
			}
		});			
	}

	@Override
	public void displayMaze(Maze3d maze) {
		if (maze == null) {

	//		this.printErrorMessage(new String[] { "Maze not found", "can't find the maze" });
			return;
		}
	//	mazeDisplay.setMazeData(maze);
		mazeDisplay.forceFocus();
		/*int[][] mazeData={
				{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
				{1,0,0,0,0,0,0,0,1,1,0,1,0,0,1},
				{0,0,1,1,1,1,1,0,0,1,0,1,0,1,1},
				{1,1,1,0,0,0,1,0,1,1,0,1,0,0,1},
				{1,0,1,0,1,1,1,0,0,0,0,1,1,0,1},
				{1,1,0,0,0,1,0,0,1,1,1,1,0,0,1},
				{1,0,0,1,0,0,1,0,0,0,0,1,0,1,1},
				{1,0,1,1,0,1,1,0,1,1,0,0,0,1,1},
				{1,0,0,0,0,0,0,0,0,1,0,1,0,0,1},
				{1,1,1,1,1,1,1,1,1,1,1,1,0,1,1},
			};
		mazeDisplay.setMazeData(mazeData);*/
	}

	@Override
	public void displayMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void start() {
		run();		
	}

	// Checkkkkkkkkkkkkkkkk
/*	@Override
	public void printAnswers(String[] msg) {

		MessageBox msgBox = new MessageBox(shell, SWT.ICON_INFORMATION);
		if (msg[0].isEmpty() == false) {
			msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false) {
			msgBox.setMessage(msg[1]);
		}
		msgBox.open();

	}

	@Override
	public void printCross(int[][] cross) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifySolutionIsReady(String name) {
		setChanged();
		notifyObservers(solutionCmd + " " + name);
	}

	@Override
	public void displayMazeSolution(Solution<Position> solution) {
		mazeDisplay.printSolution(solution);
	}
	
	
	public void displayMazeHint(Solution<Position> solution) {
		mazeDisplay.prinHint(solution);
	}

	@Override
	public void printErrorMessage(String[] msg) {
		MessageBox msgBox = new MessageBox(shell, SWT.ICON_ERROR);
		if (msg[0].isEmpty() == false) {
			msgBox.setText(msg[0]);
		}
		if (msg[1].isEmpty() == false) {
			msgBox.setMessage(msg[1]);
		}
		msgBox.open();

	}

}*/
}