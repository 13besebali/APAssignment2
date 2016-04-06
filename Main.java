import java.util.*;
import java.util.stream.IntStream;

public class Main {
	int[] board = new int[100];
	//Snake and Ladder board with initial and final points of Snake and Ladder
	static int[] s_Head =
		new int[] {10, 44, 55, 60, 68, 76, 90, 91, 96, 99};
	static int[] s_Tail =
		{3, 7, 16, 24, 35, 45, 57, 69, 74, 88};
	static int[] l_Head = 
		{39, 43, 46, 58, 62, 66, 70, 84, 97, 92, 94};
	static int[] l_Tail = {4, 6, 9, 14, 23, 32, 40, 53, 72, 80, 87};
	static int[] players, playerTrapped;
	
	//Random Number
	static Random rand = new Random();
	//turn represents the index of array Players
	static int dice, turn, n, winning_condition;
	static boolean check_six = false;
	
	public static void game(){
		turn = 0;
		winning_condition = -1;
		//check wining condition and turn of player
		while(winning_condition == -1)
		{
			System.out.println("\nIteration:\n");
			dice = rollingDice();
			System.out.println("Turn: "+turn+"\nposition of Player: "+players[turn]);
			if(players[turn]+dice <= 100){
				System.out.println("player previous turn + dice value <= 100: "+players[turn]+dice);
				if(dice != 6 && playerTrapped[turn] == 0){
					players[turn] = players[turn]+dice;
					snakes(s_Head, s_Tail, turn);
					ladders(l_Head, l_Tail, turn);
				}
				else
				{
					players[turn] = players[turn]+dice;
					check_six = true;
					playerTrapped[turn] = 0;
					snakes(s_Head, s_Tail, turn);
					ladders(l_Head, l_Tail, turn);
				}
			}
			
			System.out.println("new position of Player: "+players[turn]);
			
			//if player has reached 100 then he will win
			if(players[turn] == 100){
				winning_condition = turn;
				System.out.println("\nPlayer "+turn+" won the Game");
			}
			
			//if dice=6 then player will play again 
			if(!check_six){
				turn++;
			}else{
				check_six = false;
			}

			if(turn == n){
				turn = 0;
			}
		}
	}

	//Rolling the Dice and Generating number from 1 to 6
	private static int rollingDice() {
		int number = rand.nextInt(6) + 1;
		System.out.println("rollingDice: "+number);
		return number; 
	}
	
	//Snake Function will throw the user to the tail of snake
	private static void snakes(int[] s_Head, int[] s_Tail, int turn){
		boolean container = IntStream.of(s_Head).anyMatch(x -> x == players[turn]);
		int indexs_Head = 0;
		if(container){
			int element = players[turn];
			System.out.println("element: " + element);
			
			for(int i=0; i<s_Head.length; i++){
				if(s_Head[i] == element){
					indexs_Head = i;
				}
			}
			
			System.out.println("s_Head: " + indexs_Head);
			System.out.println("Snake is at: " + players[turn]);
			players[turn] = s_Tail[indexs_Head];
			System.out.println("Down to: " + players[turn]);
			playerTrapped[turn] = 1;
		}
	}
	
	//Ladder Function which will bring the player to its head
	private static void ladders(int[] l_Head, int[] l_Tail, int turn){
		boolean container = IntStream.of(l_Tail).anyMatch(x -> x == players[turn]);
		int indexl_Tail = 0;
		if(container){
			int element = players[turn];
			System.out.println("element: " + element);
			
			for(int i=0; i<l_Tail.length; i++){
				if(l_Tail[i] == element){
					indexl_Tail = i;
				}
			}
			
			System.out.println("l_Tail: " + indexl_Tail);
			System.out.println(" l_Tail at: " + players[turn]);
			players[turn] = l_Head[indexl_Tail];
			System.out.println("Up to: " + players[turn]);
			check_six = true;
		}
	}
	public static void main(String[] args){
		Scanner reader = new Scanner(System.in);
		System.out.print("Enter the number of Players between 2 and 4:\n");
		n = reader.nextInt();
		if(n >= 2 && n <= 4){
			playerTrapped = new int[n];
			players = new int[n];
			game();
		}
	}
	
}
