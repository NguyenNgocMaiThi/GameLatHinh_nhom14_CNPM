package Controller;

import model.Bot;
import model.Memory;

public class BotController implements Runnable{
	private Memory memory; // đc sử dụng để lưu trữ trò chơi
	private Bot bot; // đại diên 1 con bot trong trò chơi 
	private Controller controller; // đc sử dụng để quản lý trò chơi
	private Thread thread; // đc sử dụng thực thi thread của BotController

	// create the botcontroller.
	public BotController(Memory memory, Bot bot, Controller controller)
	{
		this.memory = memory;//tham số 1
		this.bot = bot;// tham số 2
		this.controller = controller;// tham số 3
	}
	
	// start the controller thread.
	public void start()
	{
		this.thread = new Thread(this, bot.getName()); //Phương thức "start" được sử dụng để bắt đầu thực thi thread của BotController
		thread.start();
	}
	
	/*** thread Execute method, detect if bot should pick.*/
	@Override
	public void run() {
		// the thread is alive while the game is alive.
		while (!memory.isFinished()) {
			if (memory.whoseTurn().equals(bot.getName())) {
				try {
					Thread.sleep(375);
				} catch (InterruptedException e) {
				} 
				memory.turn();
				controller.botMessage();
			}
			try {
				Thread.sleep((-bot.getMindfulness() * 2 + 12) * 250); // 1s, 2s, 3s, 4s  // bot 
																// time/turn.
																//để tăng hoặc giảm thời gian chờ giữa các lượt chơis
			} catch (InterruptedException e) {
				e.printStackTrace(); 
			}
		}
		System.out.println("Game Finished, Thread Stopped.");
	}

}
