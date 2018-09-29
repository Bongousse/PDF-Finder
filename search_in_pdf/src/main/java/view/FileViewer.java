package view;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import controller.FileExplorer;
import controller.PdfReader;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

public class FileViewer extends Application {
	private Stage primaryStage;
	private BorderPane rootLayout;
	private List<File> fileList;
	private ListView<File> fileListView;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("SearchPdf");
		initLayout();
		initComponent();
	}

	private void initLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(FileViewer.class.getResource("./FileViewer.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void explorerPdfEvent(String path) {
		FileExplorer fileExplorer = new FileExplorer();
		fileList = fileExplorer.searchDirectory(new File(path), true, "pdf");
		fileListView.getItems().clear();
		fileListView.getItems().addAll(fileList);
	}

	private void searchPdfEvent(String searchKey) {
		PdfReader pdfReader = new PdfReader();
		List<File> fileListBySearchKey = new LinkedList<>();
		for (File file : fileList) {
			try {
				if (pdfReader.searchStringInPdf(file, searchKey)) {
					fileListBySearchKey.add(file);
				}
			} catch (Exception e) {

			}
		}
		fileListView.getItems().clear();
		fileListView.getItems().addAll(fileListBySearchKey);
	}

	@SuppressWarnings("unchecked")
	private void initComponent() {
		Pane topPane = (Pane) rootLayout.getTop();
		TextField folderPath = (TextField) topPane.getChildren().get(1);
		Button folderPathSearchButton = (Button) topPane.getChildren().get(2);

		Pane centerPane = (Pane) rootLayout.getCenter();
		fileListView = (ListView<File>) centerPane.getChildren().get(0);
		fileListView.setItems(FXCollections.observableArrayList());
		TextField pdfSearchKey = (TextField) centerPane.getChildren().get(1);
		Button pdfSearchButton = (Button) centerPane.getChildren().get(2);

		folderPath.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					explorerPdfEvent(folderPath.getText());
				}
			}
		});

		folderPathSearchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				directoryChooser.setTitle("Open Resource File");
				File selectedFile = directoryChooser.showDialog(primaryStage);
				if (selectedFile != null) {
					folderPath.setText(selectedFile.getAbsolutePath());
				}
				explorerPdfEvent(folderPath.getText());
			}
		});

		pdfSearchKey.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode().equals(KeyCode.ENTER)) {
					searchPdfEvent(pdfSearchKey.getText());
				}
			}
		});

		pdfSearchButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				searchPdfEvent(pdfSearchKey.getText());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
