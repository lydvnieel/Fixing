package com.rebup;

import java.util.List;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class MainController {

    @FXML
    private GridPane gridEquipos;

    @FXML
    private ComboBox<String> comboFiltro;

    @FXML
    public void initialize() {
        comboFiltro.getItems().addAll("Todos", "Switch", "Router", "Adaptador", "Proyector", "Camara", "Laptop");
        comboFiltro.setValue("Todos");
        comboFiltro.setOnAction(e -> mostrarEquipos(comboFiltro.getValue()));
        mostrarEquipos("Todos");
    }

    private void mostrarEquipos(String filtro) {
        gridEquipos.getChildren().clear();

        List<String> tipos = List.of("Switch", "Router", "Adaptador", "Proyector", "Camara", "Laptop");

        int columna = 0, fila = 0;

        for (String tipo : tipos) {
            if (!filtro.equals("Todos") && !tipo.equals(filtro)) continue;

            VBox contenedor = new VBox(10);
            contenedor.setAlignment(Pos.CENTER);

            String rutaImagen = "/com/rebup/images/" + tipo.toLowerCase() + ".png";
            Image imagen = null;
            try {
                imagen = new Image(getClass().getResourceAsStream(rutaImagen));
                if (imagen.isError()) {
                    System.out.println("Error cargando imagen: " + rutaImagen);
                    continue;
                }
            } catch (Exception e) {
                System.out.println("No se encontró la imagen: " + rutaImagen);
                continue;
            }

            ImageView imageView = new ImageView(imagen);
            imageView.setFitWidth(100);
            imageView.setFitHeight(100);

            Button botonAgregar = new Button("+");
            botonAgregar.setStyle("-fx-background-color: #00907A; -fx-text-fill: white; -fx-font-size: 18px; -fx-background-radius: 10;");
            botonAgregar.setOnAction(e -> System.out.println("Seleccionado: " + tipo));

            contenedor.getChildren().addAll(imageView, new Label(tipo), botonAgregar);
            gridEquipos.add(contenedor, columna, fila);

            columna++;
            if (columna == 3) {
                columna = 0;
                fila++;
            }
        }
    }
}
