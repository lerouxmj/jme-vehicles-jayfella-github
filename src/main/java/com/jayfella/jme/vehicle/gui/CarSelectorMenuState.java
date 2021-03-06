package com.jayfella.jme.vehicle.gui;

import com.jayfella.jme.vehicle.Car;
import com.jayfella.jme.vehicle.Vehicle;
import com.jayfella.jme.vehicle.debug.DebugTabState;
import com.jayfella.jme.vehicle.debug.EnginePowerGraphState;
import com.jayfella.jme.vehicle.debug.TyreDataState;
import com.jayfella.jme.vehicle.debug.VehicleEditorState;
import com.jayfella.jme.vehicle.examples.cars.*;
import com.jayfella.jme.vehicle.input.KeyboardVehicleInputState;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Node;
import com.simsilica.lemur.Button;
import com.simsilica.lemur.Command;
import com.simsilica.lemur.component.TbtQuadBackgroundComponent;

public class CarSelectorMenuState extends AnimatedMenuState {

    private final Node scene;
    private final PhysicsSpace physicsSpace;


    public CarSelectorMenuState(Node scene, PhysicsSpace physicsSpace) {
        this.scene = scene;
        this.physicsSpace = physicsSpace;
    }

    @Override
    protected Button[] createItems() {
        Button[] buttons = new Button[] {
                new Button("Grand Tourer"),
                new Button("GTR Nismo"),
                new Button("Pickup Truck"),
                new Button("Hatchback"),
                new Button("Dune Buggy"),
                new Button("<< Back")
        };

        buttons[0].addClickCommands(source -> setVehicle(new GrandTourer(getApplication())));
        buttons[1].addClickCommands(source -> setVehicle(new GTRNismo(getApplication())));
        buttons[2].addClickCommands(source -> setVehicle(new PickupTruck(getApplication())));
        buttons[3].addClickCommands(source -> setVehicle(new HatchBack(getApplication())));
        buttons[4].addClickCommands(source -> setVehicle(new DuneBuggy(getApplication())));

        buttons[5].addClickCommands(source -> {
            animateOut(() -> {
                getStateManager().attach(new MainMenuState());
                getStateManager().detach(this);
            });
        });

        return buttons;
    }

    private void addVehicle(Car vehicle) {

        vehicle.showSpeedo(Vehicle.SpeedUnit.MPH);
        vehicle.showTacho();
        vehicle.attachToScene(scene, physicsSpace);

        vehicle.getVehicleControl().setPhysicsLocation(new Vector3f(0, 6, 0));

        // add some controls
        KeyboardVehicleInputState inputState = new KeyboardVehicleInputState(vehicle);
        // XBoxJoystickVehicleInputState inputState = new XBoxJoystickVehicleInputState(vehicle);
        getStateManager().attach(inputState);

        // engine graph GUI for viewing torqe/power @ revs
        EnginePowerGraphState enginePowerGraphState = new EnginePowerGraphState(vehicle);
        enginePowerGraphState.setEnabled(false);
        getStateManager().attach(enginePowerGraphState);

        // tyre data GUI for viewing how much grip each tyre has according to the pajecka formula.
        TyreDataState tyreDataState = new TyreDataState(vehicle);
        tyreDataState.setEnabled(false);
        getStateManager().attach(tyreDataState);

        // the main vehicle editor to modify all areas of the vehicle real-time.
        VehicleEditorState vehicleEditorState = new VehicleEditorState(vehicle);
        getStateManager().attach(vehicleEditorState);

        // vehicle debug add-on to enable/disable debug screens.
        DebugTabState debugTabState = new DebugTabState();
        getStateManager().attach(debugTabState);

        // the return to menu button.
        Button returnToMenuButton = new Button("Return to Main Menu");
        returnToMenuButton.setFontSize(16);
        ((TbtQuadBackgroundComponent)returnToMenuButton.getBackground()).setMargin(10, 5);
        returnToMenuButton.addClickCommands(new ReturnToMenuClickCommand(vehicle));
        returnToMenuButton.setLocalTranslation(
                getApplication().getCamera().getWidth() - returnToMenuButton.getPreferredSize().x - 40,
                getApplication().getCamera().getHeight() - 20,
                1
        );
        ((SimpleApplication)getApplication()).getGuiNode().attachChild(returnToMenuButton);

        vehicle.getNode().setShadowMode(RenderQueue.ShadowMode.CastAndReceive);

    }

    private void setVehicle(Car newVehicle) {
        addVehicle(newVehicle);
        getStateManager().detach(this);
    }


}
