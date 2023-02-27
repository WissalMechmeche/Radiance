/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.ktebi.gui;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.BorderCollapsePropertyValue;
import com.itextpdf.layout.properties.TextAlignment;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleFloatProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.ktebi.entities.Livre;
import tn.esprit.ktebi.entities.Panier;
import tn.esprit.ktebi.entities.User;
import tn.esprit.ktebi.services.ServiceFacture;
import tn.esprit.ktebi.services.ServiceLignePanier;
import tn.esprit.ktebi.services.ServicePanier;
import tn.esprit.ktebi.utils.MaConnexion;

/**
 * FXML Controller class
 *
 * @author Pc Anis
 */
public class FactureController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private Connection cnx;
    @FXML
    private Label Menu;
    @FXML
    private Label MenuClose;
    @FXML
    private AnchorPane slider;
    @FXML
    private Button panierBtn;
    @FXML
    private Button save_fac;
    @FXML
    private Button imprimer;

    ServiceFacture sf = new ServiceFacture();
    ServiceLignePanier lp = new ServiceLignePanier();
    ServicePanier sp = new ServicePanier();

    public FactureController() {
        cnx = MaConnexion.getInstance().getCnx();

    }

    @FXML
    private TableView<Livre> table_facture;

    @FXML
    private TableView<Livre> soustable_facture;

    @FXML
    private TableColumn<Livre, String> image;

    @FXML
    private TableColumn<Livre, Float> prix;
    @FXML
    private TableColumn<Livre, String> libelle;

    @FXML
    private TableColumn<Livre, Integer> quantite;

    @FXML
    private TableColumn<Livre, Integer> tot_qte;

    @FXML
    private TableColumn<Livre, Float> mnt_tot;

    public ObservableList<Livre> data;
    public ObservableList<Livre> data2;

    public void ShowContenuFac() {
        try {
            // Récupérer le panier de l'utilisateur
            String panierQuery = "SELECT * FROM Panier WHERE id_user=?";
            PreparedStatement panierStmt = cnx.prepareStatement(panierQuery);
            int userId = 3;
            panierStmt.setInt(1, userId);

            ResultSet panierRs = panierStmt.executeQuery();

            // Si l'utilisateur n'a pas de panier
            if (!panierRs.next()) {
                System.out.println("Le panier de l'utilisateur est vide.");
                return;
            }

            // Récupérer les lignes de panier pour le panier de l'utilisateur
            int panierId = panierRs.getInt("id_panier");
            String lignePanierQuery = "SELECT * FROM ligne_panier WHERE id_panier=?";
            float prixTotal = lp.calculerPrixTotal(panierId);

            PreparedStatement lignePanierStmt = cnx.prepareStatement(lignePanierQuery);
            lignePanierStmt.setInt(1, panierId);
            ResultSet lignePanierRs = lignePanierStmt.executeQuery();

            // Parcourir les lignes de panier et ajouter les livres correspondants à la liste
            int quantiteTotale = 0;

            TableColumn<Livre, Float> sousTotColumn = new TableColumn<>("Sous-total");
            sousTotColumn.setCellValueFactory(cellData -> {
                float sousTot = cellData.getValue().getPrix() * cellData.getValue().getUser(); //user==quantite(même type int)
                return new SimpleFloatProperty(sousTot).asObject();
            });

            sousTotColumn.setCellFactory(column -> {
                return new TableCell<Livre, Float>() {
                    @Override
                    protected void updateItem(Float item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText("");
                        } else {
                            setText(String.format("%.2f", item));
                        }
                    }
                };
            });
            sousTotColumn.setPrefWidth(110);
            table_facture.getColumns().add(sousTotColumn);

            while (lignePanierRs.next()) {
                int livreId = lignePanierRs.getInt("id_livre");
                int qte = lignePanierRs.getInt("qte");
                quantiteTotale += qte;

                String livreQuery = "SELECT libelle, image , prix FROM Livre WHERE id_livre=?";
                PreparedStatement livreStmt = cnx.prepareStatement(livreQuery);
                livreStmt.setInt(1, livreId);
                ResultSet livreRs = livreStmt.executeQuery();
                if (livreRs.next()) {
                    String image = livreRs.getString("image");
                    float prix = livreRs.getFloat("prix");
                    data.add(new Livre(qte, livreRs.getString("libelle"), image, livreRs.getFloat("prix")));
                }
            }

            //remplissage du deuxième tableau
            data2.add(new Livre(quantiteTotale, prixTotal));

            tot_qte.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("user"));
            mnt_tot.setCellValueFactory(new PropertyValueFactory<Livre, Float>("prix"));
            soustable_facture.setItems(data2);

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        quantite.setCellValueFactory(new PropertyValueFactory<Livre, Integer>("user"));
        libelle.setCellValueFactory(new PropertyValueFactory<Livre, String>("libelle"));
        image.setCellValueFactory(new PropertyValueFactory<Livre, String>("image"));
        image.setCellFactory(column -> {
            return new TableCell<Livre, String>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        try {
                            Image img = new Image(item);
                            imageView.setImage(img);
                            imageView.setFitHeight(80);
                            imageView.setPreserveRatio(true);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
        });
        prix.setCellValueFactory(new PropertyValueFactory<Livre, Float>("prix"));
        table_facture.setItems(data);
    }

    @FXML
    public void onBtnAjouterFactureClick(ActionEvent event) throws IOException, FileNotFoundException, SQLException {
        int id_user = 3;
        if (table_facture.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le tableau est vide!");
            alert.showAndWait();
            return;

        } else {

            ImprimerFacture();
            try {
                sf.ajouterFacture(id_user);
                table_facture.getItems().clear();
                soustable_facture.getItems().clear();
                ShowContenuFac();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Facture ajoutée");
                alert.setHeaderText(null);
                alert.setContentText("La facture a été ajoutée avec succès!");
                alert.showAndWait();
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout de la facture!");
                alert.showAndWait();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        data = FXCollections.observableArrayList();
        data2 = FXCollections.observableArrayList();

        ShowContenuFac();

        // TODO
    }

    @FXML
    private void showHome(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("homeCart.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    private void showPanierPage(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("cart-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

    @FXML
    public void ImprimerFacture() throws FileNotFoundException, IOException, SQLException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "Facture_" + timeStamp + ".pdf";
        String filePath = "src/tn/esprit/ktebi/gui/" + fileName;

        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf, PageSize.A4);
        // Create a font
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);

        ImageData imageData = ImageDataFactory.create("src/tn/esprit/ktebi/gui/images/ktebi.png");
        com.itextpdf.layout.element.Image ktebiImage = new com.itextpdf.layout.element.Image(imageData);
        ktebiImage.setWidth(70);
        document.add(ktebiImage);

        int userid = 3;
        Panier panier = sp.getPanierByUser(userid);
        User user = sf.getUserById(userid);

        // Add a new paragraph to the document
        Paragraph title = new Paragraph("Détails Facture").setFont(font);
        title.setBold();
        title.setFontSize(15f);
        title.setTextAlignment(TextAlignment.CENTER);

        Paragraph nomUser = new Paragraph("Client: " + user.getPrenom() + " " + user.getNom()).setFont(font);
        nomUser.setBold();

        nomUser.setFontSize(12f);
        nomUser.setTextAlignment(TextAlignment.CENTER);
        document.add(nomUser);

        Paragraph NumPanier = new Paragraph("Panier n°:" + panier.getId()).setFont(font);
        NumPanier.setBold();

        NumPanier.setFontSize(12f);
        NumPanier.setTextAlignment(TextAlignment.CENTER);
        document.add(NumPanier);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dateAuj = LocalDate.now().format(dateFormat);
        Paragraph date = new Paragraph("Tunis, le: " + dateAuj)
                .setTextAlignment(TextAlignment.RIGHT);

        Div header = new Div();
        header.add(title);
        header.add(date);

        document.add(header);

        // Add a new table to the document
        // Ajouter les lignes de panier
        Table table = new Table(new float[]{70, 70, 70, 70, 70});
        table.setMinWidth(75);
        table.setBorder(Border.NO_BORDER.NO_BORDER);
        table.setBorderCollapse(BorderCollapsePropertyValue.SEPARATE.SEPARATE);

        // Add table header cells
        Cell header1 = new Cell().add(new Paragraph("Quantité").setFont(font));
        Cell header2 = new Cell().add(new Paragraph("Nom livre").setFont(font));
        Cell header3 = new Cell().add(new Paragraph("Image").setFont(font));
        Cell header4 = new Cell().add(new Paragraph("Prix").setFont(font));
        Cell header5 = new Cell().add(new Paragraph("Sous-total").setFont(font));

        table.addHeaderCell(header1);
        table.addHeaderCell(header2);
        table.addHeaderCell(header3);
        table.addHeaderCell(header4);
        table.addHeaderCell(header5);

        for (Livre livre : data) {
            String imageUrl = livre.getImage(); // obtenir l'URL de l'image à partir de l'objet livre
            URL url = new URL(imageUrl); // créer une instance de URL à partir de l'URL de l'image
            com.itextpdf.layout.element.Image image = new com.itextpdf.layout.element.Image(ImageDataFactory.create(url));
            image.setWidth(50);

            table.addCell(String.valueOf(livre.getUser()));
            table.addCell(livre.getLibelle());
            table.addCell(image);
            table.addCell(String.valueOf(livre.getPrix()));
            table.addCell(String.valueOf(livre.getUser() * livre.getPrix()));

        }

        document.add(table);
        Paragraph total = new Paragraph("Montant total: " + data2.get(0).getPrix() + " DT");
        total.setBold();
        total.setTextAlignment(TextAlignment.CENTER.CENTER);

        document.add(total);
        document.close();
        File pdfFile = new File(filePath);
        if (pdfFile.exists()) {
            Desktop.getDesktop().open(pdfFile);
        }
    

}

@FXML
        private void showFacture(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("facture-ui.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

        window.setScene(tableViewScene);
        window.show();
    }

}
