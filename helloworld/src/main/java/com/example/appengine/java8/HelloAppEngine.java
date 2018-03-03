/**
 * Copyright 2017 Google Inc.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.java8;

// [START example]

import ai.api.model.Fulfillment;
import ai.api.model.GoogleAssistantResponseMessages;
import ai.api.model.ResponseMessage;
import ai.api.web.AIWebhookServlet;
import com.example.appengine.java8.repository.JSONManager;
import com.google.gson.Gson;
import org.json.JSONObject;
import org.json.XML;

import javax.servlet.annotation.WebServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloAppEngine", value = "/assistant")
public class HelloAppEngine extends AIWebhookServlet {

    public static final String prontosoccorso = "prontosoccorso";
    public static int PRETTY_PRINT_INDENT_FACTOR = 4;

    protected void doWebhook(AIWebhookRequest aiWebhookRequest, Fulfillment fulfillment) {
        Gson gson = new Gson();

        if (aiWebhookRequest.getResult().getAction().equals(prontosoccorso)) {

            try {

                StringBuilder response = new StringBuilder();
                URL url = new URL("https://servizi.apss.tn.it/opendata/STATOPS001.xml");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                JSONObject xmlJSONObj = XML.toJSONObject(response.toString());
                String jsonString = xmlJSONObj.toString(PRETTY_PRINT_INDENT_FACTOR);

                JSONManager statops = gson.fromJson(jsonString, JSONManager.class);

                List<ResponseMessage> messages_list = new ArrayList<>();

                GoogleAssistantResponseMessages.ResponseChatBubble simpleresponse = new GoogleAssistantResponseMessages.ResponseChatBubble();
                // setto il bubble message semplice
                GoogleAssistantResponseMessages.ResponseChatBubble.Item chat_bubble_simple = new GoogleAssistantResponseMessages.ResponseChatBubble.Item();

                List<GoogleAssistantResponseMessages.ResponseChatBubble.Item> itemList = new ArrayList<>();
 /*
                GoogleAssistantResponseMessages.ResponseListCard responseListCard = new GoogleAssistantResponseMessages.ResponseListCard();
                List<GoogleAssistantResponseMessages.CardItem> card_item_list = new ArrayList<>();

                responseListCard.setItems(card_item_list);
                messages_list.add(responseListCard);
*/
                chat_bubble_simple.setDisplayText("Ecco l'attesa media del Pronto Soccorso dell'Ospedale di Trento: " +
                        "Attesa codice bianco: " + statops.getSTATOPS().getPRONTOSOCCORSO().get(0).getATTESAMEDIA().getBIANCO() + " minuti "
                        +"Attesa codice giallo: " + statops.getSTATOPS().getPRONTOSOCCORSO().get(0).getATTESAMEDIA().getGIALLO() + " minuti "
                        +"Attesa codice verde: " + statops.getSTATOPS().getPRONTOSOCCORSO().get(0).getATTESAMEDIA().getVERDE() + " minuti "
                        +"Attesa codice rosso: " + statops.getSTATOPS().getPRONTOSOCCORSO().get(0).getATTESAMEDIA()
                        .getROSSO() + " minuti");

                chat_bubble_simple.setTextToSpeech("Ecco lo stato dell'Ospedale di Trento");

                itemList.add(chat_bubble_simple);


                // inserisco il bubble message all'interno della simpleresponse
                simpleresponse.setItems(itemList);
                messages_list.add(0, simpleresponse);

                // setto la risposta vocale di default
                fulfillment.setSpeech("Ecco lo stato dell'Ospedale di Trento");

                fulfillment.setMessages(messages_list);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

    }
}