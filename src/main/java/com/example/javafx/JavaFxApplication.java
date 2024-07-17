package com.example.javafx;

import com.example.javafx.components.Login;
import com.example.javafx.model.User;
import com.example.javafx.service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@SpringBootApplication
public class JavaFxApplication extends Application {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	private static ConfigurableApplicationContext context;

	public static void main(String[] args) {

		//SpringApplication.run(JavaFxApplication.class, args);
		Application.launch(args);
	}

	@Override
	public void init() throws Exception {
		context = new SpringApplicationBuilder(JavaFxApplication.class)
				.sources(JavaFxApplication.class)
				.run();
		context.getAutowireCapableBeanFactory().autowireBean(this);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		//Modal de login
		if(!showLoginDialog()){
			return;
		}

		//Obtener la autenticación actual
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		User authenticatedUser = userService.getUserByEmail(username);

		//Configuramos el stage y le pasamos el usuario autenticado
		primaryStage.setUserData(authenticatedUser);

		//Cargamos el archivo FXML
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Main.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);

		//Cargamos la escena
		Scene scene = new Scene(fxmlLoader.load());

		//Cargamos la escena en el escenario
		primaryStage.setScene(scene);
		primaryStage.show();

		//GridPane grid = new GridPane();
		//grid.setPadding(new Insets(10, 10, 10, 10));
		//grid.setVgap(5);
		//grid.setHgap(5);

		//Add form controls
		//TextField idField = new TextField();
		//idField.setPromptText("ID");
		//GridPane.setConstraints(idField, 0, 0);
		//grid.getChildren().add(idField);

		//TextField nameField = new TextField();
		//nameField.setPromptText("name");
		//GridPane.setConstraints(nameField, 0, 1);

		//Button addButton = new Button("Add");
		//GridPane.setConstraints(addButton, 1,0);
		//grid.getChildren().add(addButton);

		//ListView<User> listView = new ListView<>();
		//GridPane.setConstraints(listView, 0, 3,2,1);
		//grid.getChildren().add(listView);

		//addButton.setOnAction(e -> {

		//});

		//Scene scene = new Scene(grid, 300, 200);
		//primaryStage.setScene(scene);
		//primaryStage.show();
		//context = SpringApplication.run(JavaFxApplication.class);

		//String titulo = context.getBean("titulo", String.class);

		//FXMLLoader fxml = new FXMLLoader(getClass().getResource("/Main.fxml"));
		//fxml.setControllerFactory(context::getBean);
		//Scene scene = new Scene(fxml.load());
		//primaryStage.setTitle(titulo);
		//primaryStage.setScene(scene);
		//primaryStage.show();
	}

	private boolean showLoginDialog() throws Exception {

		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Login.fxml"));
		fxmlLoader.setControllerFactory(context::getBean);

		Dialog<Authentication> dialog = new Dialog<>();
		dialog.setTitle("Login");
		dialog.getDialogPane().setContent(fxmlLoader.load());

		ButtonType loginButtonType = new ButtonType("Login", ButtonBar.ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

		Login controller = fxmlLoader.getController();

		dialog.setResultConverter(dialogButton ->{
			if (dialogButton == loginButtonType){
				return authenticate(controller.getUsername().getText().trim(), controller.getPassword().getText().trim());
			}
			return null;
		});

		Optional<Authentication> result = dialog.showAndWait();
		return result.isPresent() && result.get().isAuthenticated();
	}

	private Authentication authenticate(String username, String password) {
		try{
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(username, password));
			SecurityContextHolder.getContext().setAuthentication(authentication);
			return authentication;
		} catch (AuthenticationException e){
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Login error");
			alert.setHeaderText(null);
			alert.setContentText("Invalid username or password");
			alert.showAndWait();

			return null;
		}
	}

	@Override
	public void stop() throws Exception{
		context.close();
	}

	public static ApplicationContext getContext(){
		return context;
	}
}
