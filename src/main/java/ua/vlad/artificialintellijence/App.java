package ua.vlad.artificialintellijence;

import ua.vlad.artificialintellijence.controller.Controller;
import ua.vlad.artificialintellijence.model.GeneticAlgorithm;
import ua.vlad.artificialintellijence.view.MainFrame;
import ua.vlad.artificialintellijence.view.View;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        View view = new MainFrame();
//        view.run();
        Controller controller = new Controller(view);

    }
}
