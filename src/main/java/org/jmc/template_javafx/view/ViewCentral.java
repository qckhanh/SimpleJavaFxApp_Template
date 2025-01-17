package org.jmc.template_javafx.view;


import org.jmc.template_javafx.view.App.ApplicationViewFactory;
import org.jmc.template_javafx.view.Start.StartViewFactory;

public class ViewCentral {
    private static ViewCentral viewCentral;

    private StartViewFactory startViewFactory;
    private ApplicationViewFactory applicationViewFactory;

    private ViewCentral() {}

    public static ViewCentral getInstance() {
        if (viewCentral == null) {
            viewCentral = new ViewCentral();
        }
        return viewCentral;
    }

    public StartViewFactory getStartViewFactory() {
        if (startViewFactory == null) {
            startViewFactory = new StartViewFactory();
        }
        return startViewFactory;
    }


    public ApplicationViewFactory getApplicationViewFactory() {
        if (applicationViewFactory == null) {
            applicationViewFactory = new ApplicationViewFactory();
        }
        return applicationViewFactory;
    }

}
