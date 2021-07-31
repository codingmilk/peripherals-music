import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MouseChecker extends JPanel implements MouseListener, Runnable, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	public static boolean m = true;
	
	public MouseMusic music;
    public static JFrame frame;

    public static int pointerLocX;
    public static int pointerLocY;

	public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed");
        System.out.println(e.getX() + "," + e.getY());//these co-ords are relative to the component
        pointerLocX = e.getX();
        pointerLocY = e.getY();

		m = false;
		synchronized (music.lock) {
			music.lock.notifyAll();
		}
	}
	
	public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased");
		m = true;
		synchronized (music.lock) {
			music.lock.notifyAll();
		}
	}

	@Override
	public void mouseClicked(MouseEvent paramMouseEvent) {
        System.out.println("mouseClicked");
	}

	@Override
	public void mouseEntered(MouseEvent paramMouseEvent) {
	}

	@Override
	public void mouseExited(MouseEvent paramMouseEvent) {
	}

	@Override
	public void run() {
        frame = new JFrame("MouseMusic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent newContentPane = this;
        newContentPane.setOpaque(true);
        frame.setContentPane(newContentPane);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.pack();
        frame.setVisible(true);
		addMouseListener(this);
        addMouseMotionListener(this);
		music = new MouseMusic();
		new Thread(music).start();
	}

    @Override
    public void mouseDragged(MouseEvent e) {
        if (!m){
            System.out.println("mouseDragged");
            System.out.println("mouse dragged to "+ e.getX() + "," + e.getY());//these co-ords are relative to the component
            pointerLocX = e.getX();
            pointerLocY = e.getY();
            synchronized (music.lock) {
                music.lock.notifyAll();
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
