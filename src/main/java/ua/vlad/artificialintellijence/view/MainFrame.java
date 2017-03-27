package ua.vlad.artificialintellijence.view;

import ua.vlad.artificialintellijence.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by vlad on 24.03.17.
 */
public class MainFrame extends JFrame implements View {
    private final int POINT_WIDTH = 6;
    private final int POINT_HEIGHT = 6;

    private JPanel rootPanel;
    private JButton okButton;
    private JSpinner spinner1;
    private JPanel drawPanel;
    private JButton clearButton;


    private List<Point> clickPositions;
    private Controller controller;

    public MainFrame() {
        setContentPane(rootPanel);
        setSize(500, 550);
        setVisible(true);

        this.clickPositions = new LinkedList<>();
        this.spinner1.setValue(100);

//        Graphics g = rootPanel.getGraphics();
//        g.clearRect(0, 0, 500, 500);

        rootPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                Point clickPosition = e.getPoint();
                clickPositions.add(clickPosition);
                drawPoint(clickPosition);
            }
        });

        okButton.addActionListener(event -> this.controller.processData(
                getClickPositions(),
                getGenerationsNumber()));

        clearButton.addActionListener(event -> {
            clearClickPositions();
            Graphics g = drawPanel.getGraphics();
            g.clearRect(0, 0, 500, 500);
        });


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public List<Point> getClickPositions() {
        return this.clickPositions;
    }

    public void clearClickPositions() {
        this.clickPositions.clear();
    }

    public int getGenerationsNumber() {
        return (Integer) spinner1.getValue();
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void drawPoint(Point point) {
        Graphics2D g = (Graphics2D) drawPanel.getGraphics();
        g.fillOval(point.x, point.y, POINT_WIDTH, POINT_HEIGHT);
    }

    @Override
    public void displayResult(List<Point> path) {
        Graphics2D g = (Graphics2D) drawPanel.getGraphics();
        Point prev = path.get(path.size() - 1);
        for (Point cur : path) {
            g.drawLine((int) prev.getX(), (int) prev.getY(),
                    (int) cur.getX(), (int) cur.getY());
            prev = cur;
        }
    }
}
