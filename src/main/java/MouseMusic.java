import org.jfugue.Player;



public class MouseMusic implements Runnable {

	public Object lock = new Object();

	@Override
	public void run() {
		while(MouseChecker.frame!=null) {
			synchronized (lock) {
				while(MouseChecker.m) {
					try {
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			Player player = new Player();

      		int fwidth = MouseChecker.frame.getContentPane().getWidth();
      		int fheight = MouseChecker.frame.getContentPane().getHeight();
            System.out.println("frame: " + fwidth + ", " + fheight);
            System.out.println("pointer loc: " + MouseChecker.pointerLocX +","+ MouseChecker.pointerLocY);//these co-ords are relative to the component

			int pitch = 127 - (int) (MouseChecker.pointerLocY*127/fheight);
			int volume = (int) (MouseChecker.pointerLocX*16383/fwidth);

			String musicString = "X[Volume]=" + volume + " [" + pitch + "]";
            System.out.println("musicString = " + musicString);

			player.play(musicString);
		}
	}
}
