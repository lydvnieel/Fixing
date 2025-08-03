package com.rebup;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private GridPane gridEquipos;

    @FXML
    private ComboBox<String> comboFiltro;

    @FXML
    private Button btnConfirmar;


    private boolean haySeleccion = false;

    @FXML
    public void initialize() {
        comboFiltro.getItems().addAll("Todos", "Disponible", "No disponible");
        comboFiltro.setValue("Todos");
        comboFiltro.setOnAction(e -> mostrarEquipos(comboFiltro.getValue()));
        mostrarEquipos("Todos");

        btnConfirmar.setDisable(true); 
        btnConfirmar.setOnAction(e -> mostrarAlerta());
    }

    private void mostrarEquipos(String filtro) {
        gridEquipos.getChildren().clear();

        List<String> tipos = List.of("switch", "router", "adaptador", "proyector", "camara", "laptop");

        int columna = 0, fila = 0;

        for (String tipo : tipos) {
            if (!filtro.equals("Todos")) {
                if (filtro.equals("Disponible") && tipo.equals("Adaptador")) continue;
                if (filtro.equals("No disponible") && !tipo.equals("Adaptador")) continue;
            }

            VBox contenedor = new VBox(10);
            contenedor.setAlignment(Pos.CENTER);

            String rutaImagen = "/com/rebup/images/" + tipo.toLowerCase() + ".png";
            Image imagen = null;
            try {
                imagen = new Image(getClass().getResourceAsStream(rutaImagen));
                if (imagen.isError()) continue;
            } catch (Exception e) {
                System.out.println("No se encontró la imagen: " + rutaImagen);
                continue;
            }

            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);

            StackPane stack = new StackPane(imageView);
            stack.setAlignment(Pos.TOP_RIGHT);

            Button botonAgregar = new Button("+");
            botonAgregar.setStyle(
                "-fx-background-color: #00907A; -fx-text-fill: white; " +
                "-fx-font-size: 12px; -fx-background-radius: 50%; " +
                "-fx-min-width: 22px; -fx-min-height: 22px;"
            );
            StackPane.setMargin(botonAgregar, new Insets(5, 5, 0, 0));

            botonAgregar.setOnAction(e -> {
                System.out.println("Seleccionado: " + tipo);
                haySeleccion = true;
                btnConfirmar.setDisable(false);
            });

            stack.getChildren().add(botonAgregar);

            contenedor.getChildren().addAll(stack, new Label(tipo));
            gridEquipos.add(contenedor, columna, fila);

            columna++;
            if (columna == 3) {
                columna = 0;
                fila++;
            }
        }
    }

   private void mostrarAlerta() {
    Stage alerta = new Stage();
    alerta.initModality(Modality.APPLICATION_MODAL);
    alerta.setTitle("Confirmación");

    Label mensaje = new Label("¡Préstamo confirmado!");
    mensaje.setStyle("-fx-font-size: 28px; -fx-text-fill: black;");

    Button btnContinuar = new Button("Continuar");
    btnContinuar.setStyle(
        "-fx-background-color: white; -fx-text-fill: black; " +
        "-fx-font-size: 18px; -fx-background-radius: 25; " +
        "-fx-border-color: black; -fx-border-radius: 25; " +
        "-fx-padding: 10 40;"
    );
    btnContinuar.setOnAction(e -> alerta.close());

    VBox layout = new VBox(40, mensaje, btnContinuar);
    layout.setAlignment(Pos.CENTER);
    layout.setPadding(new Insets(40));
    layout.setStyle("-fx-background-color: white; -fx-border-radius: 20; -fx-background-radius: 20;");

    Scene scene = new Scene(layout, 737, 521);
    alerta.setScene(scene);
    alerta.showAndWait();
}

}
