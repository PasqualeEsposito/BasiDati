import java.util.*;
import java.sql.*;

public class Esame {

    public static void main(String[] args) {

        String tipo, città, sql;
        int codice;
        Scanner scan = new Scanner(System.in);
        System.out.println("--------------------------------------");
        System.out.print("Username: ");
        String user = scan.next();
        System.out.print("Password: ");
        String password = scan.next();
        System.out.println("--------------------------------------");

        do {
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Smartbox?autoReconnect=true&useSSL=false&serverTimezone=UTC", user, password);

                Statement state = con.createStatement();
                ResultSet rset;

                do {
                    System.out.println("MENU:");
                    System.out.print("1.\tInserimento\n2.\tCancellazione\n3.\tUpdate\n4.\tQuery\nNN.\tExit\nScelta: ");
                    int c = scan.nextInt();

                    switch (c) {
                        case 1:
                            System.out.println("\n\nINSERIMENTO NUOVA AZIENDA\n");
                            System.out.print("Codice: ");
                            codice = scan.nextInt();
                            System.out.print("Tipo: ");
                            tipo = scan.next();
                            System.out.print("Città: ");
                            città = scan.next();
                            sql = "INSERT INTO Azienda VALUES (" + codice + ", '" + tipo + "','" + città + "' );";
                            state.execute(sql);
                            rset = state.executeQuery("SELECT * FROM Azienda");
                            System.out.println();
                            while (rset.next()) {
                                System.out.println("Codice: " + rset.getInt("Codice") + "\tTipo: " + rset.getString("Tipo") + "\tCittà: " + rset.getString("Citta"));
                            }
                            System.out.println("\n\n");
                            break;

                        case 2:
                            System.out.println("\n\nELIMINAZIONE AZIENDA\n");
                            System.out.print("Codice: ");
                            codice = scan.nextInt();
                            state.execute("DELETE FROM Azienda where codice= " + codice);
                            rset = state.executeQuery("SELECT * FROM Azienda");
                            System.out.println();
                            while (rset.next()) {
                                System.out.println("Codice: " + rset.getInt("Codice") + "\tTipo: " + rset.getString("Tipo") + "\tCittà: " + rset.getString("Citta"));
                            }
                            System.out.println("\n\n");
                            break;

                        case 3:
                            System.out.println("\n\nAGGIORNA AZIENDA\n");
                            System.out.print("Codice: ");
                            codice = scan.nextInt();
                            System.out.print("Nuovo tipo: ");
                            tipo = scan.next();
                            state.executeUpdate("UPDATE Azienda SET Tipo='" + tipo + "' where Codice= " + codice);
                            rset = state.executeQuery("SELECT * FROM Azienda");
                            System.out.println();
                            while (rset.next()) {
                                System.out.println("Codice: " + rset.getInt("Codice") + "\tTipo: " + rset.getString("Tipo") + "\tCittà: " + rset.getString("Citta"));
                            }
                            System.out.println("\n\n");
                            break;

                        case 4:
                            System.out.print("QUERY\n1. Elenco dei clienti che hanno acquistato almeno una SmartBox in tutte le Agenzie\r\n" +
                                    "2. Il cliente che ha speso di più nell'acquisto di SmartBox\r\n" +
                                    "3. I clienti che hanno usufruito di garanzia ma non di rimborso\r\n" +
                                    "4. L'Agenzia che ha creato più di 5 SmartBox\r\n" +
                                    "5. Tutte le SmartBox non scadute e che non sono state ancora utilizzate ordinate per Ddi\r\n" +
                                    "in ordine decrescente\r\n" +
                                    "6. Tutte le Aziende nella quale sono state utilizzate meno di 3 SmartBox\nScelta: ");
                            int scelta = scan.nextInt();
                            switch (scelta) {
                                case 1:
                                    //va dobbiamo verificare solo cambiando la tabella
                                    sql = "SELECT CF, Nome, Cognome\r\n" +
                                            "FROM Cliente \r\n" +
                                            "WHERE NOT EXISTS (SELECT *\r\n" +
                                            "     FROM Agenzia\r\n" +
                                            "     WHERE NOT EXISTS (SELECT *\r\n" +
                                            "          FROM Smartbox\r\n" +
                                            "          WHERE Cliente = CF AND Agenzia = Codice));";
                                    rset = state.executeQuery(sql);
                                    System.out.println("QUERY 1\n");
                                    while (rset.next()) {
                                        System.out.println("CF: " + rset.getString("CF") + "\tNome: " + rset.getString("Nome") + "\tCognome: " + rset.getString("Cognome"));
                                    }
                                    System.out.println("\n\n");
                                    break;

                                case 2:
                                    //Funziona
                                    state.execute("drop view if exists MaxAcquisto;");
                                    sql = "CREATE VIEW MaxAcquisto(Cliente, Spesa) AS\r\n" +
                                            "SELECT Cliente, SUM(Prezzo)\r\n" +
                                            "FROM Smartbox\r\n" +
                                            "GROUP BY Cliente;";
                                    state.execute(sql);

                                    sql = "SELECT Cliente, Spesa\r\n" +
                                            "FROM MaxAcquisto\r\n" +
                                            "WHERE Spesa = (SELECT MAX(Spesa) FROM MaxAcquisto);";
                                    rset = state.executeQuery(sql);
                                    System.out.println("QUERY 2\n");
                                    while (rset.next()) {
                                        System.out.println("CF: " + rset.getString("Cliente") + "\tSpesa: " + rset.getDouble("Spesa"));
                                    }
                                    System.out.println("\n\n");
                                    break;

                                case 3://tutto okay
                                    sql = "SELECT Nome, Cognome\r\n" +
                                            "FROM (Smartbox S JOIN Servizio Ser ON S.ID = Ser.Smartbox) JOIN Cliente ON CF = S.Cliente\r\n" +
                                            "WHERE Tipo LIKE \"Garanzia%\" AND S.Cliente NOT IN (SELECT Cliente\r\n" +
                                            "       FROM Smartbox S, Servizio Ser\r\n" +
                                            "       WHERE S.ID = Ser.Smartbox AND Tipo = \"Rimborso\");";
                                    rset = state.executeQuery(sql);
                                    System.out.println("QUERY 3\n");
                                    while (rset.next()) {
                                        System.out.println("Nome: " + rset.getString("Nome") + "\tCognome: " + rset.getString("Cognome"));
                                    }
                                    System.out.println("\n\n");
                                    break;


                                case 4:
                                    //funziona
                                    rset = state.executeQuery("SELECT Agenzia, Citta\r\n" +
                                            "FROM Smartbox JOIN Agenzia ON Codice = Agenzia\r\n" +
                                            "GROUP BY Agenzia\r\n" +
                                            "HAVING COUNT(*) > 5;");
                                    System.out.println("QUERY 4\n");
                                    while (rset.next()) {
                                        System.out.println("Agenzia: " + rset.getInt("Agenzia") + "\tCittà: " + rset.getString("Citta"));
                                    }
                                    System.out.println("\n\n");
                                    break;

                                case 5:
                                    //funziona ma dobbiamo inseriee campi nella tabella ,faccio stampare solo Cliente devo aggiungere gli altri
                                    rset = state.executeQuery("SELECT *\r\n" +
                                            "FROM Smartbox\r\n" +
                                            "WHERE Ddf >= CURRENT_DATE AND ID NOT IN (SELECT Smartbox FROM Prenotazione)\r\n" +
                                            "ORDER BY Ddi DESC;");
                                    System.out.println("QUERY 5\n");
                                    while (rset.next()) {
                                        System.out.println("ID: " + rset.getInt("ID") + "\tCliente: " + rset.getString("Cliente") + "\tAgenzia: " +
                                                rset.getInt("Agenzia") + "\tDdi: " + rset.getString("Ddi") + "\tDdf: " + rset.getString("Ddf")
                                                + "\tPrezzo: " + rset.getFloat("Prezzo"));
                                    }
                                    System.out.println("\n\n");
                                    break;

                                case 6:
                                    //funziona
                                    rset = state.executeQuery("SELECT Codice, Citta\r\n" +
                                            "FROM Azienda JOIN Prenotazione ON Codice = Azienda\r\n" +
                                            "GROUP BY Codice\r\n" +
                                            "HAVING COUNT(*) < 3;");
                                    System.out.println("QUERY 6\n");
                                    while (rset.next()) {
                                        System.out.println("Codice: " + rset.getInt("Codice") + "\tCittà: " + rset.getString("Citta"));
                                    }
                                    System.out.println("\n\n");
                                    break;

                                default:
                                    break;
                            }
                            break;

                        default:
                            scan.close();
                            con.close();
                            System.exit(1);
                    }
                } while (true);

            } catch (Exception e) {
                System.out.print(e);
            }
        } while (true);
    }
}