package ru.atlas.service.demo2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static javafx.concurrent.Worker.State.RUNNING;
import static javafx.concurrent.Worker.State.SCHEDULED;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FxConcurrentExample3  extends Application
{
	// Create the Buttons
	Button startButton = new Button("Start");
	Button cancelButton = new Button("Cancel");
	Button exitButton = new Button("Exit");
	Button resetButton = new Button("Reset");
	boolean onceStarted = false;

	// Create the scheduled service
	ScheduledService<ObservableList<Long>> service =
		new ScheduledService<ObservableList<Long>>()
		{
			@Override
			protected Task<ObservableList<Long>> createTask()
			{
				return new PrimeFinderTask();
			}
		};

	public static void main(String[] args)
	{
		Application.launch(args);
	}

	@Override
	public void start(Stage stage)
	{
		// Configure the scheduled service
		service.setDelay(Duration.seconds(5));
		service.setPeriod(Duration.seconds(30));
		service.setMaximumFailureCount(5);
                

		// Create the Event-Handlers for the Buttons
		startButton.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {
            	if (onceStarted)
            	{
            		service.restart();
            	}
            	else
            	{
            		service.start();
            		onceStarted = true;
            		startButton.setText("Restart");
            	}
            }
        });

		exitButton.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {
            	Platform.exit();
            }
        });

		cancelButton.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {
            	service.cancel();
            }
        });

		resetButton.setOnAction(new EventHandler <ActionEvent>()
		{
            public void handle(ActionEvent event)
            {
            	service.reset();
            }
        });

		// Enable/Disable the Reset and Cancel buttons
		cancelButton.disableProperty().bind(service.stateProperty().isNotEqualTo(RUNNING));
		resetButton.disableProperty().bind(Bindings.or(service.stateProperty().isEqualTo(RUNNING),
			service.stateProperty().isEqualTo(SCHEDULED)));

		// Create the GridPane
		GridPane pane = new WorkerStateGUI(service);

		// Create the ButtonBox
		HBox buttonBox = new HBox(5, startButton, cancelButton, resetButton, exitButton);

		// Create the BorderPane
		BorderPane root = new BorderPane();
		root.setCenter(pane);
		root.setBottom(buttonBox);

		// Set the Style-properties of the BorderPane
		root.setStyle("-fx-padding: 10;" +
				"-fx-border-style: solid inside;" +
				"-fx-border-width: 2;" +
				"-fx-border-insets: 5;" +
				"-fx-border-radius: 5;" +
				"-fx-border-color: blue;");

		// Create the Scene
		Scene scene = new Scene(root,500,400);
		// Add the scene to the Stage
		stage.setScene(scene);
		// Set the title of the Stage
		stage.setTitle("A Prime Number Finder Task");
		// Display the Stage
		stage.show();
	}
}
