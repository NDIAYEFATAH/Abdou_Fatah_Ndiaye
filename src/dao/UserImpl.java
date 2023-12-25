package dao;

import entity.Utilisateur;
import entity.Role;

import java.io.Console;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserImpl implements IUser{
    private DB db = new DB();
    private ResultSet rs;
    private int ok;

    @Override
    public int addUser(Utilisateur utilisateur) {
        String sql = "INSERT INTO Utilisateur(email,password,passwordHashed,id_role) VALUES(?,?,?,?)";
        try {
            db.initPrepar(sql);
            db.getPstm().setString(1,utilisateur.getEmail());
            db.getPstm().setString(2,utilisateur.getPasswordHashed());
            db.getPstm().setString(3,utilisateur.getPasswordHashed());

            Role role = utilisateur.getId_role();
            if (role !=null){
                int RoleId =role.getId();
                db.getPstm().setInt(4,RoleId);
            }


            ok = db.executeMaj();
            db.clossConnection();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return ok;
    }

    @Override
    public int getExistingRoleIdFromRoleTable() {
        int existingRoleId = -1;
        String sql = "SELECT id FROM role WHERE id=2"; // Vous pouvez ajuster cette requête selon votre base de données
        try {
            db.initPrepar(sql);
            ResultSet rs = db.executeSelect();

            if (rs.next()) {
                existingRoleId = rs.getInt("id");
            }

            db.clossConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return existingRoleId;
    }

    @Override
    public boolean authenticateUser(String email, String password) {
        boolean estAuthentifie = false;
        Utilisateur util = new Utilisateur();
        String sql = "SELECT COUNT(*) AS count FROM utilisateur WHERE email = ? AND passwordHashed = ?";
        try{
            db.initPrepar(sql);
            db.getPstm().setString(1,email);
            db.getPstm().setString(2,password);
            rs = db.executeSelect();
            if (rs.next()){
                int count = rs.getInt("count");
                estAuthentifie = count >0;
            }
            db.clossConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return estAuthentifie;
    }
    public void connexion()
    {
        PasswordHash hashPassword = new PasswordHash();
        Scanner scan = new Scanner(System.in);
        System.out.println("Donnez votre email");
        String emails = scan.nextLine();
        System.out.println("Donner le mot de passe");
        String mdp = scan.nextLine();
//        String hashedPassword = hashPassword.generatePasswordHash(mdp, "1234567890ABCDEF");
        boolean estAuthenfi = authenticateUser(emails,mdp);
        if (estAuthenfi){
            System.out.println("Bienvenue");
        }else {
            System.out.println("Echec de la connexion");
        }
    }

    @Override
    public List<Utilisateur> list() {
        List<Utilisateur> utilisateurs =new ArrayList<>();
        String sql ="SELECT * FROM utilisateur u JOIN role r ON u.id_role = r.id";
        try{
            db.initPrepar(sql);
            rs = db.executeSelect();
            while (rs.next()){
                Utilisateur util = new Utilisateur();
                util.setId_user(rs.getInt("u.id_user"));
                util.setEmail(rs.getString("u.email"));
//                IRole roledao = new RoleImpl();
//                Role role = roledao.get(rs.getInt("r.id"));
//                util.setId_role(role);
                Role role = new Role();
                role.setId(rs.getInt("r.id"));
                role.setNom(rs.getString("r.nom"));
                util.setId_role(role);
                utilisateurs.add(util);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return utilisateurs;
    }

    @Override
    public Utilisateur get(int id) {
        return null;
    }

    public void ajouterUser()
    {
        Utilisateur util = new Utilisateur();
        Scanner scan = new Scanner(System.in);
        PasswordHash hashPassword = new PasswordHash();
        String email = getEmailFromUser(scan);
        System.out.println("Donner le mot de passe");
//        String password = scan.nextLine();
        String password = hidePasswordInput();
        util.setPasswordHashed(hashPassword.generatePasswordHash(password,"1234567890ABCDEF"));
        int existingRoleId = getExistingRoleIdFromRoleTable();
        if (existingRoleId != -1) {
            Role rl = new Role();
            rl.setId(existingRoleId);
            util.setId_role(rl);
        } else {
            System.out.println("Aucun ID de rôle existant trouvé dans la table role.");
        }
        util.setEmail(email);
        util.setPassword(password);
        util.setPasswordHashed(password);
        int ok = addUser(util);
        if (ok==1){
            System.out.println("User enregistrer avec succes !");
        }else {
            System.out.println("Echec d'enregistrement");
        }
    }
    private static String getEmailFromUser(Scanner scanner) {
        String email;
        boolean isValidEmail = false;

        do {
            System.out.println("Donner votre email : ");
            email = scanner.nextLine();

            // Utilisation de regex pour valider l'e-mail
            if (isValidEmail(email)) {
                isValidEmail = true;
            } else {
                System.out.println("L'e-mail n'est pas valide.");
            }
        } while (!isValidEmail);

        return email;
    }
    private static boolean isValidEmail(String email) {
        // Pattern pour valider l'e-mail se terminant par @gmail.com
        String emailRegex = ".*@gmail\\.com$";
        return Pattern.matches(emailRegex, email);
    }
    private static String hidePasswordInput() {
        Console console = System.console();
        if (console != null) {
            char[] passwordChars = console.readPassword();
            return new String(passwordChars);
        } else {
            // Efface les caractères entrés pour masquer le mot de passe
            String password = "";
            try {
                while (true) {
                    char c = (char) System.in.read();
                    if (c == '\r' || c == '\n') {
                        break;
                    } else {
                        password += c;
                    }
                }
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
            return password;
        }
    }
}
