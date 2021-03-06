package com.jayfella.jme.vehicle.tire;

import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.Container;
import com.simsilica.lemur.TabbedPanel;
import com.simsilica.lemur.props.PropertyPanel;

public class TireEditor extends Container {

    public TireEditor(PajeckaTireModel tireModel) {

        PropertyPanel latProps = createPropertyPanel(tireModel.getLateral());
        PropertyPanel lngProps = createPropertyPanel(tireModel.getLongitudinal());
        PropertyPanel mntProps = createPropertyPanel(tireModel.getAlignMoment());

        TabbedPanel tabbedPanel = new TabbedPanel();
        tabbedPanel.addTab("Lateral", latProps);
        tabbedPanel.addTab("Longitudinal", lngProps);
        tabbedPanel.addTab("Align Moment", mntProps);

        PropertyPanel tireProps = new PropertyPanel("glass");
        tireProps.addFloatProperty("Load", tireModel, "load", 0, tireModel.getMaxLoad(), 0.1f);

        Button printButton = new Button("Print Data");
        printButton.addClickCommands((Command<Button>) source -> {
            System.out.println(tireModel.toString());
        });

        addChild(tabbedPanel);
        addChild(tireProps);
        addChild(printButton);
    }

    private PropertyPanel createPropertyPanel(TyreSettings tyreSettings) {

        PropertyPanel propertyPanel = new PropertyPanel("glass");

        // maximum load
        // propertyPanel.addFloatProperty("Max Load", tyreSettings, "maxLoad", 1, 20000, 0.001f);

        // slip angle

        // affects how quickly grip will drop
        propertyPanel.addFloatProperty("SlipAngle C", tyreSettings, "slipAngleCoefficientC", 0, 5, 0.001f);

        // affects how "tight" the curve is.
        propertyPanel.addFloatProperty("SlipAngle B", tyreSettings, "slipAngleCoefficientB", 0, 30, 0.001f);

        // affects how much the slip "drops off".0...
        propertyPanel.addFloatProperty("SlipAngle E", tyreSettings, "slipAngleCoefficientE", -2, 2, 0.001f);

        // load / force
        // I'm not certain of the difference between these two...

        // affects how much load can be applied.
        propertyPanel.addFloatProperty("Load KA", tyreSettings, "loadCoefficientKA", 0, 10, 0.001f);

        // affects how much load can be applied to the tyre.
        propertyPanel.addFloatProperty("Load KB", tyreSettings, "loadCoefficientKB", 0, 0.0005f, 0.000001f);

        return propertyPanel;
    }

}
