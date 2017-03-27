package ua.vlad.artificialintellijence.view;

import ua.vlad.artificialintellijence.controller.Controller;

import java.awt.*;
import java.util.List;

/**
 * Created by vlad on 24.03.17.
 */
public interface View {
    void setController(Controller controller);
    void displayResult(List<Point> path);
}
