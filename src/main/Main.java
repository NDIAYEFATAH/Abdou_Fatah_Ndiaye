package main;

import dao.IUser;
import dao.PasswordHash;
import dao.UserImpl;
import entity.Role;
import entity.Utilisateur;

import java.io.Console;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        IUser userdao = new UserImpl();
        Scanner scan = new Scanner(System.in);
        Utilisateur util = new Utilisateur();

        while (true) {

            System.out.println("Menu:");
            System.out.println("1. Connexion");
            System.out.println("2. Inscription");
            System.out.println("3. Quitter");


            System.out.print("Choisissez une option (1-3): ");
            int choice = scan.nextInt();
            scan.nextLine();


            switch (choice) {
                case 1:
                    System.out.println("Option choisie : Connexion");
                    userdao.connexion();
                    while (true)
                    {
                        System.out.println("Menu:");
                        System.out.println("1. Ajouter User");
                        System.out.println("2. Lister");
                        System.out.println("3. Quitter");
                        System.out.print("Choisissez une option (1-4): ");
                        int choix = scan.nextInt();
                        scan.nextLine();

                        switch (choix)
                        {
                            case 1:
                                System.out.println("Option choisie : Inscription");
                                scan.nextLine();
                                userdao.ajouterUser();
                                break;
                            case 2:
                                System.out.println("Option choisie : Lister");
                                System.out.println("Liste des Utilisateurd");
                                List<Utilisateur> utilisateurs = userdao.list();
                                System.out.println("---------------------------------------------------------------");
                                for (Utilisateur utilisateur : utilisateurs) {
                                        System.out.println("ID: " + utilisateur.getId_user() + " Email: " +
                                                utilisateur.getEmail() + " Role: " + utilisateur.getId_role().getNom());

                                    System.out.println("---------------------------------------------------------------");
                                }
                                break;
                            case 4:
                                System.out.println("Au revoir !");
                                System.exit(0);
                            default:
                                System.out.println("Choix invalide. Veuillez choisir une option valide.");
                        }
                        break;
                    }
                case 2:
                    System.out.println("Option choisie : Inscription");
                    userdao.ajouterUser();
                    break;
                case 3:
                    System.out.println("Au revoir !");
                    System.exit(0);
                default:
                    System.out.println("Choix invalide. Veuillez choisir une option valide.");
            }
            System.out.println("\n--- Appuyez sur Enter pour continuer ---");
            scan.nextLine();
            System.out.println("\n-------------------------\n");
        }
    }


}