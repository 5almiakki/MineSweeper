package mineSweeper;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class MineSweeper extends JFrame {
	
	MineSweeper ms = this;
	// �⺻ ���̵��� ����
	MineField mineField = new MineField(this, "Easy");
	// �޴����� mineField�� ������ �� �ֵ��� mineField�� �ѱ� 
	MenuBar mb = new MenuBar(this);
	Container c;
	
	public MineSweeper() {
		setTitle("MineSweeper");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(mb);
		GridBagConstraints gbc = new GridBagConstraints();
		
		c = getContentPane();
		float[] hsbcolor = Color.RGBtoHSB(191, 222, 255, null);
		c.setBackground(Color.getHSBColor(hsbcolor[0], hsbcolor[1], hsbcolor[2]));
		c.setLayout(new GridBagLayout());
		
		// ���� ��ư �����, ���콺 ������ �߰�
		JButton resetButton = new JButton("Reset");
		resetButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				mineField = new MineField(ms, mineField.getDifficulty());
				gbc.gridx = 1;
				gbc.gridy = 3;
				c.remove(2);
				c.add(mineField, gbc);
				setVisible(false);
				setVisible(true);
			}
		});
		
		// ���� ��ư�� �����ӿ� �߰�
		gbc.gridx = 1;
		gbc.gridy = 1;
		c.add(resetButton, gbc);
		
		// ������ �����ӿ� �߰�
		JLabel emptyLabel = new JLabel(" ");
		emptyLabel.setFont(new Font(null, Font.PLAIN, 20));
		gbc.gridx = 1;
		gbc.gridy = 2;
		c.add(emptyLabel, gbc);
		
		// mineField�� �����ӿ� �߰�
		gbc.gridx = 1;
		gbc.gridy = 3;
		c.add(mineField, gbc);
		
		setSize(450, 500);
		setLocation(500, 200);
		setVisible(true);
	}

}

class MenuBar extends JMenuBar {
	
	JMenu difficultyMenu = new JMenu("Difficulty");
	JMenuItem[] difficultyItem = new JMenuItem[4];
	String[] difficultyName = {"Easy", "Medium", "Hard", "Custom"};
	
	public MenuBar(MineSweeper ms) {
		// ���̵� ������ ��ü ����, �� �����ۿ� �׼� ������ �߰�
		for (int i = 0; i < 4; i++) {
			difficultyItem[i] = new JMenuItem(difficultyName[i]);
		}
		difficultyItem[0].addActionListener(new MenuActionListener(ms));
		difficultyItem[1].addActionListener(new MenuActionListener(ms));
		difficultyItem[2].addActionListener(new MenuActionListener(ms));
		difficultyItem[3].addActionListener(new MenuActionListener(ms));
		
		// ���̵� �޴��� ���̵� ������ ���, ���̵� �޴��� �޴��ٿ� ���
		for (JMenuItem jmi : difficultyItem) {
			difficultyMenu.add(jmi);
		}
		add(difficultyMenu);
	}
	
	private class MenuActionListener implements ActionListener {
		
		private MineSweeper ms;
		
		public MenuActionListener(MineSweeper ms) {
			this.ms = ms;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String difficulty = ((JMenuItem)e.getSource()).getText();
			ms.mineField = new MineField(ms, difficulty);
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 1;
			gbc.gridy = 3;
			ms.c.remove(2);
			ms.c.add(ms.mineField, gbc);
			// ���̵� ���� �� minefield�� �ٷ� �� �ߴ� ���� �����ؾ� ��. 
		}
		
	}
	
}

class MineField extends JPanel {

	private FieldButton[][] field;
	private int x, y, mines;
	private String difficulty;
	
	public MineField(MineSweeper ms, String difficulty) {
		this.difficulty = difficulty;
		switch (difficulty) {
		case "Easy" :
			x = 9;
			y = 9;
			mines = 10;
			ms.setSize(0, 0);
			ms.setSize(450, 500);
			setLayout(new GridLayout(y, x));
			field = new FieldButton[y][x];
			putButton();
			break;
		case "Medium" :
			x = 16;
			y = 16;
			mines = 39;
			ms.setSize(0, 0);
			ms.setSize(700, 750);
			setLayout(new GridLayout(y, x));
			field = new FieldButton[y][x];
			putButton();
			break;
		case "Hard" :
			x = 30;
			y = 16;
			mines = 81;
			ms.setSize(0, 0);
			ms.setSize(1200, 750);
			setLayout(new GridLayout(y, x));
			field = new FieldButton[y][x];
			putButton();
			break;
		case "Custom" :
			new CustomDialog(ms).setVisible(true);
			break;
		}
	}

	public FieldButton[][] getField() {
		return field;
	}
	
	public int getx() {
		return x;
	}
	
	public int gety() {
		return y;
	}

	public int getMines() {
		return mines;
	}

	public void setField(FieldButton[][] field) {
		this.field = field;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setMines(int mines) {
		this.mines = mines;
	}
	
	public String getDifficulty() {
		return difficulty;
	}

	public void putButton() {
		// mines��ŭ ���� ����
//		for (FieldButton fb1[] : field) {
//			for (FieldButton fb2 : fb1) {
//				if (mines > 0) {
//					fb2 = new FieldButton(true);
//				}
//				else {
//					fb2 = new FieldButton(false);
//				}
//				mines--;
//			}
//		}
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (mines > 0) {
					field[i][j] = new FieldButton(this, true);
				}
				else {
					field[i][j] = new FieldButton(this, false);
				}
//				field[i][j].setText(" ");
				field[i][j].setIcon(new ImageIcon("./src/resources/icons/blank.png"));
				mines--;
			}
		}
		
		// ��ư�� ����
//		for (FieldButton fb1[] : field) {
//			for (FieldButton fb2 : fb1) {
//				FieldButton temp = field[(int)(Math.random() * y)][(int)(Math.random() * x)];
//				temp = swap(fb2, fb2 = temp);
//			}
//		}
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				int randx = (int)(Math.random() * x);
				int randy = (int)(Math.random() * y);
				field[randy][randx] = swap(field[i][j], field[i][j] = field[randy][randx]);
			}
		}
		
		// ��ư �ֺ� ���� ������ŭ mineNear ����
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				
				for (int n = i - 1; n <= i + 1; n++) {
					for (int m = j - 1; m <= j + 1; m++) {
						if (n == i && m == j) {
							continue;
						}
						
						try {
							if (field[n][m].isMineButton()) {
								field[i][j].incMineNear();
							}
						} catch (ArrayIndexOutOfBoundsException e) {}
					}
				}
				
				add(field[i][j]);
			}
		}
//		for (int i = 0; i < y; i++) {
//			for (int j = 0; j < x; j++) {
//				if (field[i][j].isMineButton()) {
//					System.out.print("@ ");
//				}
//				else if (field[i][j].getMineNear() == 0) {
//					System.out.print("  ");
//				}
//				else {
//					System.out.print(field[i][j].getMineNear() + " ");
//				}
//			}
//			System.out.println();
//		}
	}
	
	public FieldButton swap(FieldButton fb1, FieldButton fb2) {
		return fb1;
	}
	
	public void showAll() {
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (!field[i][j].isMineButton() && field[i][j].isFlag()) {
					field[i][j].setIcon(new ImageIcon("./src/resources/icons/wrongFlag.png"));
				}
				else {
					field[i][j].show();
				}
				field[i][j].setPressed(true);
			}
		}
	}
	
	public void gameOver() {
		boolean gameOver = true;
		for (int i = 0; i < y; i++) {
			for (int j = 0; j < x; j++) {
				if (field[i][j].isFlag() && !field[i][j].isMineButton() || !field[i][j].isFlag() && field[i][j].isMineButton()) {
					gameOver = false;
				}
			}
		}
		if (gameOver) {
			showAll();
			JDialog wrongInput = new JDialog();
			wrongInput.setLocation(700, 400);
			wrongInput.setSize(180, 70);
			wrongInput.setLayout(null);
			
			JLabel wrongText = new JLabel("��� ���ڸ� ã�ҽ��ϴ�.", JLabel.CENTER);
			wrongText.setFont(new Font("���� ���", Font.BOLD, 13));
			wrongText.setLocation(0, 6);
			wrongText.setSize(160, 15);
			
			wrongInput.add(wrongText);
			wrongInput.setVisible(true);
		}
	}
	
}

class FieldButton extends JLabel {

	private MineField mf;
	private boolean mineButton;
	private boolean pressed;
	private boolean flag;
	private int mineNear;

	public FieldButton(MineField mf, boolean mineButton) {
		this.mf = mf;
		this.mineButton = mineButton;
		addMouseListener(new FieldButtonAdapter());
	}
	
	public int getMineNear() {
		return mineNear;
	}

	public void incMineNear() {
		mineNear++;
	}

	public boolean isMineButton() {
		return mineButton;
	}

	public void setMineButton(boolean mineButton) {
		this.mineButton = mineButton;
	}
	
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	public void setFlag() {
		if (!pressed) {
			if (!flag) {
				setIcon(new ImageIcon("./src/resources/icons/flag.png"));
				flag = true;
				mf.gameOver();
			}
			else {
				setIcon(new ImageIcon("./src/resources/icons/blank.png"));
				flag = false;
			}
		}
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void show() {
		if (pressed || flag);
		else if (mineButton) {
//			setText("@");
			setIcon(new ImageIcon("./src/resources/icons/mine.png"));
//			setIcon(new ImageIcon("./src/resources/icons/���.jpg"));
//			setIcon(new ImageIcon("./src/resources/icons/����.jpg"));
			pressed = true;
		}
//		else if (mineNear == 0) {
//			
//			pressed = true;
//		}
		else {
//			setText(mineNear + "");
			setIcon(new ImageIcon("./src/resources/icons/" + mineNear + ".png"));
			pressed = true;
		}
	}
	
	public void showZero(FieldButton[][] f, int locY, int locX) {
		if (!f[locY][locX].pressed && !f[locY][locX].isMineButton() && (f[locY][locX].mineNear == 0)) {
			f[locY][locX].show();
			try {
				showZero(f, locY - 1, locX);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY + 1, locX);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY, locX - 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY, locX + 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY - 1, locX - 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY + 1, locX + 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY + 1, locX - 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
			try {
				showZero(f, locY - 1, locX + 1);
			} catch (ArrayIndexOutOfBoundsException e) {}
		}
		else if (!f[locY][locX].pressed && !f[locY][locX].isMineButton() && (f[locY][locX].mineNear > 0)) {
			f[locY][locX].show();
		}
	}
	
	private class FieldButtonAdapter extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			FieldButton fb = (FieldButton)e.getSource();
			if (e.getButton() == MouseEvent.BUTTON3) {
				fb.setFlag();
			}
			else if (e.getButton() == MouseEvent.BUTTON1){
				FieldButton[][] f = mf.getField();
				if (mineButton && !flag) {
					for (int i = 0; i < mf.gety(); i++) {
						for (int j = 0; j < mf.getx(); j++) {
							f[i][j].show();
						}
					}
					mf.showAll();
				}
				else if (mineNear == 0) {
					for (int i = 0; i < mf.gety(); i++) {
						for (int j = 0; j < mf.getx(); j++) {
							if (f[i][j] == fb) {
								showZero(f, i, j);
							}
						}
					}
				}
				else {
					fb.show();
				}
			}
		}
		
	}
	
}

class CustomDialog extends JDialog {
	
	JLabel[] inputName = new JLabel[3];
	JTextField[] input = new JTextField[3];
	JButton ok = new JButton("OK");
	MineSweeper ms;
	
	public CustomDialog(MineSweeper ms) {
		this.ms = ms;
		setTitle("Custom");
		setLocation(600, 300);
		setSize(140, 200);
		setLayout(null);
		
		inputName[0] = new JLabel("x");
		inputName[1] = new JLabel("y");
		inputName[2] = new JLabel("mines");
		
		for (int i = 0; i < 3; i++) {
//			inputName[i].setFont(new Font("���� ���", Font.PLAIN, 13));
			input[i] = new JTextField();
			inputName[i].setSize(40, 20);
			input[i].setSize(50, 20);
			inputName[i].setLocation(10, 10 + (30 * i));
			input[i].setLocation(60, 10 + (30 * i));
			add(inputName[i]);
			add(input[i]);
		}
		
		ok.addMouseListener(new CustomMouseAdapter());
		ok.setSize(60, 25);
		ok.setLocation(10, 110);
		add(ok);
	}
	
	private class CustomMouseAdapter extends MouseAdapter {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				ms.mineField.setX(Integer.parseInt(input[0].getText()));
				ms.mineField.setY(Integer.parseInt(input[1].getText()));
				ms.mineField.setMines(Integer.parseInt(input[2].getText()));
				ms.setSize(0, 0);
				ms.setSize(ms.mineField.getx() * 36 + 128, ms.mineField.gety() * 36 + 193);
				ms.mineField.setLayout(new GridLayout(ms.mineField.gety(), ms.mineField.getx()));
				ms.mineField.setField(new FieldButton[ms.mineField.gety()][ms.mineField.getx()]);
				ms.mineField.putButton();
				setVisible(false);
			} catch (NumberFormatException nfe) {
				JDialog wrongInput = new JDialog();
				wrongInput.setLocation(700, 400);
				wrongInput.setSize(150, 70);
				wrongInput.setLayout(null);
				
				JLabel wrongText = new JLabel("���ڸ� �Է��ϼ���.", JLabel.CENTER);
				wrongText.setFont(new Font("���� ���", Font.BOLD, 13));
				wrongText.setLocation(0, 6);
				wrongText.setSize(130, 15);
				
				wrongInput.add(wrongText);
				wrongInput.setVisible(true);
			}
		}
		
	}
	
}
