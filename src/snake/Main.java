package snake;
import ui.SnakeUserInterface;
import java.util.ArrayList;
import ui.UIAuxiliaryMethods;
import ui.UserInterfaceFactory;

public class Main {
	
	public static String addPoint(String point) {
		int tmp = Integer.valueOf(point);
		tmp++;
		return Integer.toString(tmp);
	}
	
	public static String score = "0";
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {

		//Instances of classes
		UIAuxiliaryMethods uia = new UIAuxiliaryMethods();
		UserInterfaceFactory uif = new UserInterfaceFactory();
		SnakeUserInterface sui =  uif.getSnakeUI(32, 24);
			
		//Size of snake
		ArrayList<Point> map = new ArrayList<Point>();
		ArrayList<Point> snake = new ArrayList<Point>();
		ArrayList<Point> snakebody = new ArrayList<Point>();
		
		//Start point of snake
		Point startPoint = new Point(3,0);
		map.add(startPoint);
		
		//Initialize snake
		Point head = new Point(startPoint.x, startPoint.y);
		snake.add(head);
		Point tail = new Point(startPoint.x-1,startPoint.y);
		snake.add(tail);
		Point n = new Point(startPoint.x-2,startPoint.y);
		snake.add(n);
		
		//Generate apple
		Point apple = new Point(uia.getRandom(0, 32), uia.getRandom(0, 24));
		
		//Game setups
		Boolean game = true;
		int choice = 2;
		int beforeChoice = 10;
		sui.setFramesPerSecond(12);
		
		//Game loop
		while(game) {
			for(int i =1; i<snake.size(); i++) {
				snakebody.add(snake.get(i));
			}
			
			System.out.println(snakebody.get(0).x + " " + snake.get(0).x);
			if(snakebody.contains(snake.get(0))) {
				System.out.println("KPASAMA");
			}
			//Check if the snake over the screen
			System.out.println(snake.get(0).x + " " + snakebody.get(1).x);
			if(snake.get(0).x >=32 || snake.get(0).y >= 24 || snake.get(0).x<0 || snake.get(0).y<0) {
				uia.showMessage("Game over your score: " + score);
				game = false;
				break;
			}

			sui.clearStatusBar();
			sui.printf("Point: " + score);
			
			//Spawn apple
			sui.place(apple.x, apple.y, sui.FOOD);
			
			//When snake's head hit to the apple:
			/*
			 * Increase point,
			 * Change apple coordinates
			 * Add snake's tail one more node
			 */
			
			if(apple.x == snake.get(0).x && apple.y == snake.get(0).y) {
				score = addPoint(score);
				apple.x = uia.getRandom(0, 32); 
				apple.y = uia.getRandom(0, 24);
				Point newNode = new Point(snake.get(snake.size()-1).x, snake.get(snake.size()-1).y);
				snake.add(newNode);
			}
			String data = sui.getEvent().data;
			if(data == "R") {
				choice = 1;
			}else if(data == "D") {
				choice = 2;
			}else if(data == "L") {
				choice = 3;
			}else if(data == "U") {
				choice = 4;
			}
			
			if(choice == 3) { //If snake goes right cannot go left immediately
				if(beforeChoice == 1)
					choice = 1;
			}else if (choice == 2) { //If snake goes down cannot go up immediately
				if(beforeChoice == 4)
					choice = 4;
			}else if (choice == 1) { //If snake goes left cannot go right immediately
				if(beforeChoice == 3)
					choice = 3;
			}else if (choice == 4) { //If snake goes up cannot go down immediately
				if(beforeChoice == 2)
					choice = 2;
			}
			
			switch(choice) {
				case 1: //Go right
					beforeChoice = 1;
					for(int i = snake.size()-1; i>=0; i--) {
						sui.place(snake.get(i).x, snake.get(i).y, sui.SNAKE);
						if(i!=0) {
							snake.get(i).x = snake.get(i-1).x;
							snake.get(i).y = snake.get(i-1).y;
						}
					}
					sui.showChanges();	
					snake.get(0).x+=1;
					break;
				case 2: //Go Down
					beforeChoice = 2;
					for(int i = snake.size()-1; i>=0; i--) {
						sui.place(snake.get(i).x, snake.get(i).y, sui.SNAKE);
						if(i!=0) {
							snake.get(i).x = snake.get(i-1).x;
							snake.get(i).y = snake.get(i-1).y;
						}
					}
					sui.showChanges();	
					snake.get(0).y+=1;
					break;
				case 3: //Go Left
					beforeChoice = 3;
					for(int i = snake.size()-1; i>=0; i--) {
						sui.place(snake.get(i).x, snake.get(i).y, sui.SNAKE);
						if(i!=0) {
							snake.get(i).x = snake.get(i-1).x;
							snake.get(i).y = snake.get(i-1).y;
						
						}
					}
					sui.showChanges();
					snake.get(0).x-=1;
					break;
				case 4: //Go Up
					beforeChoice = 4;
					for(int i = snake.size()-1; i>=0; i--) {
						sui.place(snake.get(i).x, snake.get(i).y, sui.SNAKE);
						if(i!=0) {
							snake.get(i).x = snake.get(i-1).x;
							snake.get(i).y = snake.get(i-1).y;
						}
					}
					sui.showChanges();	
					snake.get(0).y-=1;
					break;
			}

			sui.clear();
		}
	}

}