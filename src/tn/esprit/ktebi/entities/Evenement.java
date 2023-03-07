package tn.esprit.ktebi.entities;

import java.time.LocalDate;

import java.util.Date;

/**
 *
 * @author MSI
 */
public class Evenement {

    private int id;
    private String Nomevent;

    private String description;
    private String lieu;
    private float prix;
    private LocalDate date_evenement;
    private Theme theme;
    private User user;
    private String image;
    private int id_theme;
    private String nom;
    private int nbrparticipant;

    public Evenement() {

    }

    public Evenement(int id) {
        this.id = id;

    }

    public Evenement(int id, String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, User user, String image, int id_theme) {
        this.id = id;
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
        this.image = image;
        this.id_theme = id_theme;
        this.nom = nom;
    }

    public Evenement(String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, Theme theme, User user, String image, int id_theme) {
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
        this.image = image;
        this.id_theme = id_theme;
    }

    public Evenement(String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, String image, int id_theme) {
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.image = image;
        this.id_theme = id_theme;
    }

    public Evenement(int id, String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, String image, String nom) {
        this.id = id;
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.image = image;
        this.nom = nom;
    }

    public Evenement(String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, User user, String image, int id_theme) {
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.user = user;
        this.image = image;
        this.id_theme = id_theme;
    }

    public Evenement(int id, String Nomevent, String lieu, String description, float prix,Theme theme, String image) {
        this.id = id;
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.theme = theme;
        this.image = image;

    }

    public Evenement(int id, String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, Theme theme, User user, String image, int nbrparticipant) {
        this.id = id;
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
        this.image = image;
        this.nbrparticipant = nbrparticipant;
    }
    

    public Evenement(int aInt, String nom, String lieu, float prix, String desc, String image) {
        this.id = aInt;
        this.Nomevent = nom;
        this.lieu = lieu;
        this.prix = prix;
        this.description = desc;
        this.image = image;

    }

    /**
     *
     * @param aInt
     * @param nom
     * @param lieu
     * @param prix
     * @param desc
     * @param date
     * @param image
     */
    public Evenement(int aInt, String nom, String lieu, float prix, String desc, LocalDate date, String image) {
        this.id = aInt;
        this.Nomevent = nom;
        this.lieu = lieu;
        this.prix = prix;
        this.description = desc;
        this.image = image;
        this.date_evenement = date;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomevent() {
        return Nomevent;
    }

    public void setNomevent(String Nomevent) {
        this.Nomevent = Nomevent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public LocalDate getDate_evenement() {
        return date_evenement;
    }

    public void setDate_evenement(LocalDate date_evenement) {
        this.date_evenement = date_evenement;
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getImage() {
        return image;
    }

    public int getIdtheme() {
        return id_theme;
    }

    public void setIdtheme(int id_theme) {
        this.id_theme = id_theme;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getNbrparticipant() {
        return nbrparticipant;
    }

    public void setNbrparticipant(int nbrparticipant) {
        this.nbrparticipant = nbrparticipant;
    }

    @Override
    public String toString() {
        return "Evenement{" + "id=" + id + ", Nomevent=" + Nomevent + ", description=" + description + ", lieu=" + lieu + ", prix=" + prix + ", date_evenement=" + date_evenement + ", theme=" + theme + ", user=" + user + ", image=" + image + ", id_theme=" + id_theme + ", nom=" + nom + '}';
    }

    public Evenement(String Nomevent, String description, String lieu, float prix, String image, int id_theme) {
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.image = image;
        this.id_theme = id_theme;
    }

    public Evenement(String Nomevent, String description, String lieu, float prix, LocalDate date_evenement, Theme theme, User user, String image, int id_theme, String nom) {
        this.Nomevent = Nomevent;
        this.description = description;
        this.lieu = lieu;
        this.prix = prix;
        this.date_evenement = date_evenement;
        this.theme = theme;
        this.user = user;
        this.image = image;
        this.id_theme = id_theme;
        this.nom = nom;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Evenement other = (Evenement) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

}