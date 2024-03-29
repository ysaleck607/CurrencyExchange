package com.example.devise.Paiement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.UUID;
import org.json.JSONObject;

@Service
@AllArgsConstructor
public class MobilePaiementService {
    public String sandBoxUserProvisionning (){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/v1_0/apiuser";
            URL url = new URL(urlString);
            String uuid = UUID.randomUUID().toString();

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Request headers
            connection.setRequestProperty("X-Reference-Id", uuid);

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "5bed7697326541ccba21ce10a05069c7");

            connection.setRequestMethod("POST");

            // Request body
            connection.setDoOutput(true);
            connection
                    .getOutputStream()
                    .write(
                            "{ providerCallbackHost: \"string\" }".getBytes()
                    );

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);

            connection.disconnect();
            return uuid;
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
        return null;
    }

    public String createApiKey(String id){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/v1_0/apiuser/"+id+"/apikey";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Définition de l'en-tête Content-Length
            String postData = ""; // Vous pouvez mettre les données à envoyer ici
            byte[] postDataBytes = postData.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            //Request headers
            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "5bed7697326541ccba21ce10a05069c7");

            connection.setRequestMethod("POST");

            // Envoi des données si nécessaire
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postDataBytes);
            }

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);


            connection.disconnect();

            return content.toString();
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
        return null;
    }

    public String generateToken(String id, String key){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/collection/token/";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Trouver la position de ":" pour obtenir le début de la valeur
            int colonIndex = key.indexOf(":") + 2; // Ajouter 2 pour sauter le ":\""

            // Trouver la position de "}" pour obtenir la fin de la valeur
            int bracketIndex = key.lastIndexOf("\"");

            // Extraire la valeur en utilisant substring
            String apiKey = key.substring(colonIndex, bracketIndex);

            // Encode API user ID and API key in Base64
            String authString = id+":"+apiKey;
            String encodedAuthString = Base64.getEncoder().encodeToString(authString.getBytes());

            // Définition de l'en-tête Content-Length
            String postData = ""; // Vous pouvez mettre les données à envoyer ici
            byte[] postDataBytes = postData.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            //Request headers
            connection.setRequestProperty("Authorization", "Basic "+encodedAuthString);

            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "5bed7697326541ccba21ce10a05069c7");

            connection.setRequestMethod("POST");

            // Envoi des données si nécessaire
            connection.setDoOutput(true);
            try (OutputStream os = connection.getOutputStream()) {
                os.write(postDataBytes);
            }

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);

            connection.disconnect();
            return content.toString();
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
        return null;
    }

    public void requestToPay(String tokenObject){
        try {
            String urlString = "https://sandbox.momodeveloper.mtn.com/collection/v1_0/requesttopay";
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String uuid = UUID.randomUUID().toString();

            JSONObject jsonObject = new JSONObject(tokenObject);

            // Extraire la valeur du jeton d'accès
            String accessToken = jsonObject.getString("access_token");

            // Définition de l'en-tête Content-Length
            String postData = ""; // Vous pouvez mettre les données à envoyer ici
            byte[] postDataBytes = postData.getBytes("UTF-8");
            connection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));

            //Request headers
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);

            connection.setRequestProperty("X-Reference-Id", uuid);

            connection.setRequestProperty("X-Target-Environment", "sandbox");

            connection.setRequestProperty("Content-Type", "application/json");

            connection.setRequestProperty("Cache-Control", "no-cache");

            connection.setRequestProperty("Ocp-Apim-Subscription-Key", "5bed7697326541ccba21ce10a05069c7");

            connection.setRequestMethod("POST");

            // Request body
            connection.setDoOutput(true);
            connection
                    .getOutputStream()
                    .write(
                            ("{ \"amount\": \"600\", \"currency\": \"EUR\", \"externalId\": \"3467\"," +
                                    " \"payer\": {     \"partyIdType\": \"MSISDN\",     \"partyId\": \"0022966053684\" }," +
                                    " \"payerMessage\": \"Hi\", \"payeeNote\": \"Thanks\" }").getBytes()
                    );

            int status = connection.getResponseCode();
            System.out.println(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream())
            );
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println(content);

            connection.disconnect();
        } catch (Exception ex) {
            System.out.print("exception:" + ex.getMessage());
        }
    }
}